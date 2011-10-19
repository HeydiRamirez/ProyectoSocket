/**
 *
 * @author Miguel Peinado
 * @author Timoteo Ponce 
 */
import java.net.*;
import java.io.*;

class Cliente{
 public static void main (String[] args){
        DataInputStream input;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        int in;
        byte[] byteArray;
        final String filename = "/opt/jdk-1.6.bin";

  try{
            final File localFile = new File( filename );
            Socket client = new Socket("10.23.3.20", 1234);
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());

            //enviamos el nombre del archivo            
            DataOutputStream dos=new DataOutputStream(client.getOutputStream());
            dos.writeUTF(localFile.getName());

            byteArray = new byte[8192];
            while ((in = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
            }

            bis.close();
            bos.close();

        }catch ( Exception e ) {
            System.err.println(e);
        }
    }
}

//y en la parte del Servidor:

/**
 *
 * @author Miguel Peinado
 */
import java.net.*;
import java.io.*;

class Servidor{
    public static void main (String[] args){

        ServerSocket server;
        Socket connection;

        DataOutputStream output;
        BufferedInputStream bis;
        BufferedOutputStream bos;

        byte[] receivedData;
        int in;
        String file;

        try{
            server = new ServerSocket( 1234 );
            while ( true ) {
                connection = server.accept();

                receivedData = new byte[1024];
                bis = new BufferedInputStream(connection.getInputStream());
                DataInputStream dis=new                 DataInputStream(connection.getInputStream());
            
                //recibimos el nombre del fichero
                file = dis.readUTF();
                file = file.substring(file.indexOf('/')+1,file.length());

                bos = new BufferedOutputStream(new FileOutputStream(file));
                while ((in = bis.read(receivedData)) != -1){
                    bos.write(receivedData,0,in);
                }
                bos.close();
                dis.close();         
            }
        }catch (Exception e ) {
            System.err.println(e);
        }
    }
}
