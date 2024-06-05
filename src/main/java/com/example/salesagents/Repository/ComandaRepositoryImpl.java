package com.example.salesagents.Repository;

import com.example.salesagents.Domain.Comanda;
import com.example.salesagents.Domain.Produs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class ComandaRepositoryImpl implements ComandaRepository{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ComandaRepositoryImpl(Properties props) {
        logger.info("Initializin ComandaRepositoryImpl with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Optional save(Comanda comanda) {
        logger.traceEntry("Saving comanda {} ", comanda);
        Connection con = dbUtils.getConnection();
        if(comanda.getCantitate() > comanda.getProdus().getCantitate()){
            logger.traceExit("Stoc insuficient");
            return Optional.empty();}
        else
        {
            try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO comenzi(agent_id,produs_id,cantitate) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                preStmt.setLong(1, comanda.getAgent().getId());
                preStmt.setLong(2, comanda.getProdus().getId());
                preStmt.setInt(3, comanda.getCantitate());
                int resultStmt = preStmt.executeUpdate();
                if (resultStmt > 0) {
                    logger.trace("Saved {} instances", comanda);
                    logger.traceExit("Command saved successfully: {}", comanda);
                    updateStoc(con,comanda.getProdus(),comanda);
                    return Optional.of(comanda);
                }
            } catch (SQLException ex) {
                logger.error(ex);
                System.err.println("Error DB " + ex);
            }
        }
        logger.traceExit();
        return Optional.empty();
    }

    private void updateStoc(Connection con, Produs produs,Comanda comanda) {
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE produse SET cantitate = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
            preStmt.setInt(1, produs.getCantitate()-comanda.getCantitate());
            preStmt.setLong(2, produs.getId());
            int resultStmt = preStmt.executeUpdate();
            if (resultStmt > 0) {
                logger.trace("Updated {} instances", produs);
                logger.traceExit("Stoc updated successfully: {}", produs);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
    }

    @Override
    public Optional getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional update(Comanda comanda) {
        return Optional.empty();
    }

    @Override
    public Optional deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }
}
