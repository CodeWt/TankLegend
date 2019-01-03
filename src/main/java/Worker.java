import config.Configurator;
import server.PSServer;

public class Worker {
    public static void main(String[] args){
        PSServer psServer = null;
        try {
            psServer = PSServer.newInstance();
            psServer.bind(Configurator.getServerHost(),Configurator.getServerPort());
            psServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}