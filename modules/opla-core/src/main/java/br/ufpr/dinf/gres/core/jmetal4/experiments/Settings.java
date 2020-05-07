//  Settings.java
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package br.ufpr.dinf.gres.core.jmetal4.experiments;

import br.ufpr.dinf.gres.common.exceptions.JMException;
import br.ufpr.dinf.gres.core.jmetal4.core.Algorithm;
import br.ufpr.dinf.gres.core.jmetal4.core.Operator;
import br.ufpr.dinf.gres.core.jmetal4.core.Problem;
import br.ufpr.dinf.gres.core.jmetal4.encodings.solutionType.ArrayRealSolutionType;
import br.ufpr.dinf.gres.core.jmetal4.encodings.solutionType.BinaryRealSolutionType;
import br.ufpr.dinf.gres.core.jmetal4.encodings.solutionType.BinarySolutionType;
import br.ufpr.dinf.gres.core.jmetal4.encodings.solutionType.RealSolutionType;
import br.ufpr.dinf.gres.core.jmetal4.operators.crossover.Crossover;
import br.ufpr.dinf.gres.core.jmetal4.operators.crossover.CrossoverFactory;
import br.ufpr.dinf.gres.core.jmetal4.operators.mutation.Mutation;
import br.ufpr.dinf.gres.core.jmetal4.operators.mutation.MutationFactory;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Class representing Settings objects.
 */
public abstract class Settings {
    protected Problem problem_;
    protected String problemName_;
    protected String paretoFrontFile_;

    public Settings() {
    } // Constructor

    public Settings(String problemName) {
        problemName_ = problemName;
    } // Constructor

    /**
     * Default configure method
     *
     * @return A problem with the default configuration
     * @throws JMException default exception
     */
    abstract public Algorithm configure() throws JMException;

    /**
     * Configure method. Change the default configuration
     *
     * @param settings settings
     * @return A problem with the settings indicated as argument
     * @throws JMException            default exception 1
     * @throws ClassNotFoundException default exception 2
     */
    public final Algorithm configure(HashMap settings) throws JMException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
        if (settings != null) {
            Field[] fields = this.getClass().getFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().endsWith("_")) { // it is a configuration field
                    // The configuration field is an integer
                    if (fields[i].getType().equals(int.class) ||
                            fields[i].getType().equals(Integer.class)) {
                        if (settings.containsKey(fields[i].getName())) {
                            Integer value = (Integer) settings.get(fields[i].getName());
                            fields[i].setInt(this, value.intValue());
                        }
                    } else if (fields[i].getType().equals(double.class) ||
                            fields[i].getType().equals(Double.class)) {
                        Double value = (Double) settings.get(fields[i].getName());

                        if (settings.containsKey(fields[i].getName())) {
                            if (fields[i].getName().equals("mutationProbability_") &&
                                    value == null) {
                                if ((problem_.getSolutionType().getClass() == RealSolutionType.class) ||
                                        (problem_.getSolutionType().getClass() == ArrayRealSolutionType.class)) {
                                    value = 1.0 / problem_.getNumberOfVariables();
                                } else if (problem_.getSolutionType().getClass() == BinarySolutionType.class ||
                                        problem_.getSolutionType().getClass() == BinaryRealSolutionType.class) {
                                    int length = problem_.getNumberOfBits();
                                    value = 1.0 / length;
                                } else {
                                    int length = 0;
                                    for (int j = 0; j < problem_.getNumberOfVariables(); j++) {
                                        length += problem_.getLength(j);
                                    }
                                    value = 1.0 / length;
                                }
                                fields[i].setDouble(this, value);
                            } // if
                            else {
                                fields[i].setDouble(this, value);
                            }
                        }
                    } else {
                        Object value = settings.get(fields[i].getName());
                        if (value != null) {
                            if (fields[i].getType().equals(Crossover.class)) {
                                Object value2 = CrossoverFactory.getCrossoverOperator((String) value, settings);
                                value = value2;
                            }

                            if (fields[i].getType().equals(Mutation.class)) {
                                Object value2 = MutationFactory.getMutationOperator((String) value, settings);
                                value = value2;
                            }

                            fields[i].set(this, value);
                        }
                    }
                }
            } // for

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType().equals(Crossover.class) ||
                        fields[i].getType().equals(Mutation.class)) {
                    Operator operator = (Operator) fields[i].get(this);
                    String tmp = fields[i].getName();
                    String aux = fields[i].getName().substring(0, tmp.length() - 1);

                    for (int j = 0; j < fields.length; j++) {
                        if (i != j) {
                            if (fields[j].getName().startsWith(aux)) {
                                tmp = fields[j].getName().substring(aux.length(), fields[j].getName().length() - 1);

                                if (
                                        (fields[j].get(this) != null)) {
                                    if (fields[j].getType().equals(int.class) ||
                                            fields[j].getType().equals(Integer.class)) {
                                        operator.setParameter(tmp, (double) fields[j].getInt(this));
                                    } else if (fields[j].getType().equals(double.class) ||
                                            fields[j].getType().equals(Double.class)) {
                                        operator.setParameter(tmp, fields[j].getDouble(this));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            paretoFrontFile_ = (String) settings.get("paretoFrontFile_");
        }
        return configure();
    } // configure
} // Settings