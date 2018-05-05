package br.com.brendon.tcc2.database;

import org.mapsforge.core.model.LatLong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.brendon.tcc2.beans.PontoDeOnibus;
import br.com.starmetal.database.ConnectionFactory;
import br.com.starmetal.exceptions.DatabaseException;
import br.com.starmetal.io.IOArquivo;
import br.com.starmetal.io.IOProperties;

public class DAO {

    private Connection connection;

    public DAO(){
        this.connection = new ConnectionFactory().getDefaultConnection();
    }

    public List<PontoDeOnibus> consultarPontosDeOnibus(String query){

        if(query == null || query.isEmpty()){
            return null;
        }

        List<PontoDeOnibus> listaDePontos = new ArrayList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("select * from \"URBS\".gurbs_linestop_201704 where line_code = '020';");
            ResultSet result = statement.executeQuery();

            while(result.next()){
                PontoDeOnibus pontoDeOnibus = getPontoDeOnibus(result);
                listaDePontos.add(pontoDeOnibus);
            }

        } catch(SQLException sqle){
            throw new DatabaseException("Falha ao executar consulta por Pontos de Ônibus na base de Dados: " + sqle.getMessage());
        }

        return listaDePontos;
    }

    private PontoDeOnibus getPontoDeOnibus(ResultSet result) throws SQLException{

        PontoDeOnibus pontoDeOnibus = new PontoDeOnibus();

        pontoDeOnibus.setLineCode   (result.getString("line_code"));
        pontoDeOnibus.setAddress    (result.getString("address"));
        pontoDeOnibus.setNum        (result.getInt   ("num"));
        pontoDeOnibus.setLat        (result.getDouble("lat"));
        pontoDeOnibus.setLon        (result.getDouble("lon"));
        pontoDeOnibus.setSeq        (result.getInt   ("seq"));
        pontoDeOnibus.setDirection  (result.getString("direction"));
        pontoDeOnibus.setType       (result.getString("type"));
        pontoDeOnibus.setGid        (result.getInt   ("gid"));
        pontoDeOnibus.setGeom       (result.getString("geom"));

        return pontoDeOnibus;
    }

    public List<LatLong> getLatLonPontosDeOnibus(List<PontoDeOnibus> listaDePontos){

        List<LatLong> listaLatLon = new ArrayList<>();

        for(PontoDeOnibus ponto : listaDePontos){
            listaLatLon.add( ponto.getLatLong() );
        }

        return listaLatLon;
    }

    public static void main(String[] args) {

//        String path = IOProperties.DEFAULT_PROPERTIES_FOLDER_PATH + IOArquivo.SEPARADOR_DE_DIRETORIO + "db.properties";

//        Properties properties = IOProperties.getProperties(path);

        DAO dao = new DAO();

        List<PontoDeOnibus> lista = dao.consultarPontosDeOnibus("dummy");

        for(PontoDeOnibus ponto : lista){
            System.out.println(ponto.getLineCode() + ";" +
                                ponto.getLat() + ";" +
                                ponto.getLon() + ";" +
                                ponto.getNum());
        }

    }
}
