package vanzari;

import model.Vanzare;
import model.VanzariLocuri;
import spectacole.SpectacolRepository;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VanzareRepository {

    private final JDBCUtils dbUtils;

    public VanzareRepository() {
        dbUtils=new JDBCUtils();
    }

    public Vanzare add(Vanzare entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into vanzari (id_spectacol, data_vanzare) values(?,?)")){
            setPreparedStatement(entity,preStmt);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }

        int finalId = getId();
        entity.getLista_locuri_vandute().forEach(loc -> {
            addVanzariLocuri(con, finalId, loc);
        });
        return null;
    }

    private synchronized int getId() {
        Connection con=dbUtils.getConnection();
        int id = -1;

        try(PreparedStatement preStmt=con.prepareStatement("select last_insert_rowid() as id")){
            ResultSet result=preStmt.executeQuery();
            id = result.getInt("id");
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return id;
    }

    private void addVanzariLocuri(Connection con, int id_vanzare, int numar_loc) {

        try(PreparedStatement preStmt= con.prepareStatement("insert into vanzari_locuri (id_vanzare, numar_loc) values(?,?)")){
            setPreparedStatementVanzariLocuri(id_vanzare, numar_loc, preStmt);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
    }


    private void setPreparedStatementVanzariLocuri(int ID_vanzare, int nrLocuri,PreparedStatement preStmt) throws SQLException {
        preStmt.setInt(1, ID_vanzare);
        preStmt.setInt(2, nrLocuri);
    }

    private void setPreparedStatement(Vanzare entity,PreparedStatement preStmt) throws SQLException {
        preStmt.setInt(1, entity.getID_spectacol());
        preStmt.setDate(2, entity.getData_vanzare());
    }

    public Vanzare getOne(Long id) {
        Connection con=dbUtils.getConnection();
        Vanzare vanzare=null;
        try(PreparedStatement preStmt=con.prepareStatement("select * from vanzari where id=?")){
            preStmt.setLong(1,id);
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    vanzare=getEntityFromResultSet(result);
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }

        vanzare.setLista_locuri_vandute(getListaLocuriVandute(vanzare.getID_vanzare()));
        return vanzare;
    }

    public boolean isBooked(int id_spectacol, int seat) {
        Connection con=dbUtils.getConnection();
        boolean booked = false;
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as count from vanzari V inner join vanzari_locuri VL where V.id_spectacol = ? and VL.id_vanzare=V.id and VL.numar_loc = ?")){
            preStmt.setLong(1,id_spectacol);
            preStmt.setLong(2,seat);
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int count = result.getInt("count");
                    if(count>0)
                        booked=true;
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }

        return booked;
    }

    public List<Integer> getListaLocuriVandute(int id){
        Connection con=dbUtils.getConnection();
        VanzariLocuri vanzariLocuri;
        List<Integer> lista = new ArrayList<>();

        try(PreparedStatement preStmt=con.prepareStatement("select * from vanzari_locuri where id_vanzare=?")){
            preStmt.setLong(1,id);
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){

                    vanzariLocuri=getEntityFromResultSetVanzariLocuri(result);
                    lista.add(vanzariLocuri.getNumar_loc());
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return lista;
    }

    private VanzariLocuri getEntityFromResultSetVanzariLocuri(ResultSet result) throws SQLException {
        int id_vanzare=result.getInt("id_vanzare");
        int numar_loc=result.getInt("numar_loc");

        return new VanzariLocuri(id_vanzare, numar_loc);
    }

    private Vanzare getEntityFromResultSet(ResultSet result) throws SQLException {
        int id=result.getInt("id");
        int id_spectacol=result.getInt("id_spectacol");
        Date dataVanzarel=result.getDate("data_vanzare");

        return new Vanzare(id, id_spectacol, dataVanzarel);
    }

    public Iterable<Vanzare> getAll() {
        Connection con=dbUtils.getConnection();
        List<Vanzare> vanzari=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from vanzari")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    Vanzare vanzare=getEntityFromResultSet(result);
                    vanzare.setLista_locuri_vandute(getListaLocuriVandute(vanzare.getID_vanzare()));
                    vanzare.setNr_bilete_vandute(vanzare.getLista_locuri_vandute().size());

                    vanzare.setSuma(vanzare.getNr_bilete_vandute()*new SpectacolRepository().getOne(vanzare.getID_spectacol()).getPret_bilet());
                    vanzari.add(vanzare);
                }
            }
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
        return vanzari;
    }



    public void nuke() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from vanzari")){
            preStmt.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }

        try(PreparedStatement preStmt=con.prepareStatement("delete from vanzari_locuri")){
            preStmt.executeUpdate();
        }catch (SQLException ex){
            System.err.println("Error DB"+ex);
        }
    }
}
