package br.ufpr.dinf.gres.core.persistence;

import br.ufpr.dinf.gres.core.jmetal4.metrics.Elegance;
import br.ufpr.dinf.gres.core.jmetal4.metrics.Wocsinterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WocsinterfacePersistence {

    private Connection connection;

    public WocsinterfacePersistence(Connection connection) {
	this.connection = connection;
    }

    public void save(Wocsinterface wocsInterface) {

	String executionID = "null";
	if (wocsInterface.getExecutionResults() != null)
	    executionID = wocsInterface.getExecutionResults().getId();

	StringBuilder query = new StringBuilder();

	query.append("insert into WocsinterfaceMetrics (wocsInterface, execution_id, is_all, experiement_id, id_solution) values (");
	query.append(wocsInterface.getWocsInterface());
	query.append(",");
	query.append(executionID);
	query.append(",");
	if (wocsInterface.getExecutionResults() == null)
	    query.append("1");
	else
	    query.append("0");
	query.append(",");
	query.append(wocsInterface.getExperiement().getId());
	query.append(",");
	query.append(wocsInterface.getIdSolution());
	query.append(")");

	try {
	    Statement statement = connection.createStatement();
	    statement.executeUpdate(query.toString());
	} catch (SQLException ex) {
	    Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}