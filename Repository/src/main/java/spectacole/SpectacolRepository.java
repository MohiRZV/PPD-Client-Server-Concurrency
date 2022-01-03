package spectacole;

import model.Spectacol;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpectacolRepository {

    private final JDBCUtils dbUtils;

    public SpectacolRepository() {
        dbUtils=new JDBCUtils();
    }

    public Spectacol add(Spectacol entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into spectacole (data_spectacol,titlu,pret_bilet) values(?,?,?)")){
            setPreparedStatement(entity,preStmt);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return null;
    }

    private void setPreparedStatement(Spectacol entity,PreparedStatement preStmt) throws SQLException {
        preStmt.setDate(1, entity.getData_spectacol());
        preStmt.setString(2, entity.getTitlu());
        preStmt.setDouble(3, entity.getPret_bilet());
    }

    public Spectacol getOne(int id) {
        Connection con=dbUtils.getConnection();
        Spectacol spectacol=null;
        try(PreparedStatement preStmt=con.prepareStatement("select * from spectacole where id_spectacol=?")){
            preStmt.setLong(1,id);
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    spectacol=getEntityFromResultSet(result);
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return spectacol;
    }

    private Spectacol getEntityFromResultSet(ResultSet result) throws SQLException {
        int id=result.getInt("id_spectacol");
        Date dataSpectacol=result.getDate("data_spectacol");
        String titlu = result.getString("titlu");
        double pretBilet = result.getDouble("pret_bilet");

        return new Spectacol(id, dataSpectacol, titlu, pretBilet);
    }

    public Iterable<Spectacol> getAll() {
        Connection con=dbUtils.getConnection();
        List<Spectacol> spectacole=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from spectacole")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    Spectacol spectacol=getEntityFromResultSet(result);
                    spectacole.add(spectacol);
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return spectacole;
    }

    public static void main(String[] args) {
        Spectacol spectacol1 = new Spectacol(Date.valueOf("2022-01-05"), "12 Furiosi", 100);
        Spectacol spectacol2 = new Spectacol(Date.valueOf("2022-01-07"), "Chirita in carantina", 200);
        Spectacol spectacol3 = new Spectacol(Date.valueOf("2022-01-08"), "Nu mai tine linia ocupata", 150);

        SpectacolRepository repo = new SpectacolRepository();
        repo.add(spectacol1);
        repo.add(spectacol2);
        repo.add(spectacol3);
    }
}
