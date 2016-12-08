package utilities;

import java.net.InetAddress;

/**
 * Created by Shower on 2016/12/8 0008.
 */
public class RouterId {
    public InetAddress address;
    public int port;

    public RouterId(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public String toString() {
        return address.toString() + ":" + port;
    }
}
