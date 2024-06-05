package com.example.salesagents.Repository;

import com.example.salesagents.Domain.Entity;
import com.example.salesagents.Domain.Produs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class ProdusRepositoryImpl implements ProdusRepository{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ProdusRepositoryImpl(Properties props) {
        logger.info("Initializin ProdusRepositoryImpl with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Optional save(Entity obj) {
        return Optional.empty();
    }

    @Override
    public Optional getById(Object o) {
        return Optional.empty();
    }

    @Override
    public Optional update(Entity obj) {
        return Optional.empty();
    }

    @Override
    public Optional deleteById(Object o) {
        return Optional.empty();
    }

    @Override
    public List<Produs> getAll() {
        logger.traceEntry("Getting all products");
        List<Produs> produse = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM produse");
             ResultSet resultSet = preStmt.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Integer cantitate = resultSet.getInt("cantitate");
                String nume = resultSet.getString("nume");
                Double pret = resultSet.getDouble("pret");
                String descriere = resultSet.getString("descriere");
                Produs produs = new Produs(id, nume,pret,cantitate,descriere);
                produse.add(produs);
            }
            logger.traceExit("Retrieved {} produse",produse.size());
            return produse;
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return produse;
        }
    }
}
