package utilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shower on 2016/12/8 0008.
 */
public class RoutingMessage implements Serializable{
    public RouterId routerId;
    public Map<RouterId, Integer> neighbours;

    public RoutingMessage(RouterId routerId) {
        this.routerId = routerId;
        neighbours = new HashMap<>();
    }
}
