package com.example.salesagents.Repository;

import com.example.salesagents.Domain.Agent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class AgentRepositoryImpl implements AgentRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public AgentRepositoryImpl(Properties props) {
        logger.info("Initializing AgentRepositoryImpl with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Agent> save(Agent agent) {
        logger.traceEntry("Saving trial {} ", agent);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO agents(username,password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preStmt.setString(1, agent.getUsername());
            preStmt.setString(2, agent.getPassword());
            int result = preStmt.executeUpdate();
            if (result > 0) {
                ResultSet generatedKeys = preStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    agent.setId(id);
                    logger.trace("Saved {} instances", result);
                    logger.traceExit("Agent saved successfully: {}", agent);
                    return Optional.of(agent);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<Agent> getById(Long id) {
        logger.traceEntry("Getting trial with id {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM agents WHERE agents.id = ?")) {
            preStmt.setLong(1, id);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String name = result.getString("username");
                    String password = result.getString("password");
                    Agent agent = new Agent(id, name, password);
                    logger.trace("Found agent {}", agent);
                    return Optional.of(agent);
                } else {
                    logger.traceExit("Agent with id {} not found", id);
                    return Optional.empty();
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Agent> update(Agent agent) {
        logger.traceEntry("Updating agent {}", agent);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE agents SET username = ?, password = ? WHERE id = ?")) {
            preStmt.setString(1, agent.getUsername());
            preStmt.setString(2, agent.getPassword());
            preStmt.setLong(3, agent.getId());
            int result = preStmt.executeUpdate();
            if (result > 0) {
                logger.trace("Updated {} instances", result);
                logger.traceExit("Agent updated successfully: {}", agent);
                return Optional.of(agent);
            } else {
                logger.trace("No instances updated");
                logger.traceExit("Failed to update agent: {}", agent);
                return Optional.empty();
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Agent> deleteById(Long id) {
        logger.traceEntry("Deleting agent with id {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("DELETE FROM agents WHERE id = ?")) {
            preStmt.setLong(1, id);
            int result = preStmt.executeUpdate();
            if (result > 0) {
                logger.trace("Deleted {} instances", result);
                logger.traceExit("Agent with id {} deleted successfully", id);
                return Optional.of(new Agent(id, null, null)); // Returning a placeholder Agent object
            } else {
                logger.trace("No instances deleted");
                logger.traceExit("Failed to delete trial with id {}", id);
                return Optional.empty();
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return Optional.empty();
        }
    }

    @Override
    public List<Agent> getAll() {
        logger.traceEntry("Getting all agents");
        List<Agent> agents = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM agents");
             ResultSet resultSet = preStmt.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Agent agent = new Agent(id, username, password);
                agents.add(agent);
            }
            logger.traceExit("Retrieved {} agents", agents.size());
            return agents;
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return agents;
        }
    }

    @Override
    public Agent getByUsernameAndPassword(String username, String password) {
        logger.traceEntry("Getting agent with username {} and password {}", username, password);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM agents WHERE username = ? AND password = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Long id = result.getLong("id");
                    String name = result.getString("username");
                    String pass = result.getString("password");
                    Agent agent = new Agent(id, name, pass);
                    logger.trace("Found agent {}", agent);
                    return agent;
                } else {
                    logger.traceExit();
                    return null;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            return null;
        }
    }
}
