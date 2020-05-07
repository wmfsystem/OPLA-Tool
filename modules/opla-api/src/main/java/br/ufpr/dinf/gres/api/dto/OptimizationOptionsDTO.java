package br.ufpr.dinf.gres.api.dto;

import br.ufpr.dinf.gres.api.gateway.OptimizationAlgorithm;
import br.ufpr.dinf.gres.core.jmetal4.operators.FeatureMutationOperators;
import br.ufpr.dinf.gres.core.jmetal4.metrics.ObjectiveFunctions;

import java.util.Arrays;
import java.util.List;

public class OptimizationOptionsDTO {
    public List<OptimizationAlgorithm> algorithms = Arrays.asList(OptimizationAlgorithm.values());
    public List<ObjectiveFunctions> objectiveFunctions = Arrays.asList(ObjectiveFunctions.values());
    public List<FeatureMutationOperators> featureMutationOperators = Arrays.asList(FeatureMutationOperators.values());
}
