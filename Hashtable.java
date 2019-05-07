import java.util.ArrayList;
import java.util.Arrays;

public class Hashtable<k,v> {
    private HashNode<k,v>[] slots;
    private int num_items;
    private final double lambda = 0.75;

    public Hashtable(){
        slots = new HashNode[2027];
        num_items = 0;
    }

    boolean containsKey(String key){
        String found = get(key);
        if(found == null)
            return false;
        else
            return true;
    }

    String get(String key){
        int slot = getSlot(key);
        HashNode node = slots[slot];
        while (node != null && node.getKey() != key)
            node = node.getNext();

        if(!(node == null))
            return  (String) node.getData();
        else
            return null;
    }

    void put(String key, String value){
        HashNode newnode = new HashNode(key,value);
        int hash = getSlot(key);
        HashNode node = slots[hash];
        if(node != null) {
            while (node.getNext() != null)
                node = node.getNext();
            node.setNext(newnode);
        }
        else{
            slots[hash] = newnode;
        }
        num_items++;
        Double length = 1.0*slots.length;
        double needGrowth = (1.0*num_items)/ length;
        if( needGrowth >= lambda)
            this.grow();
    }

    private void grow() {
        HashNode[] temp = new HashNode[slots.length*2];
        for(int i = 0; i < slots.length; i++){
            HashNode node = slots[i];

            if(node != null) {
                HashNode next = node.getNext();
                node.setNext(null);

                int slot = node.getKey().hashCode();
                slot = slot % temp.length;
                slot = Math.abs(slot);
                HashNode n = temp[slot];
                if(n!= null) {
                    while (n.getNext() != null)
                        n = n.getNext();
                    n.setNext(node);
                }else{
                    temp[slot] = node;
                }
                while(next != null){
                    node = next;
                    next = node.getNext();
                    node.setNext(null);

                    slot = node.getKey().hashCode();
                    slot = slot % temp.length;
                    slot = Math.abs(slot);
                    n = temp[slot];
                    if(n!= null) {
                        while (n.getNext() != null)
                            n = n.getNext();
                        n.setNext(node);
                    }else{
                        temp[slot] = node;
                    }
                }
            }
        }
        slots = temp;
    }


    String remove(String key){
        int slot = getSlot(key);
        String val;
        if(slots[slot].getKey() == key){
            val = (String)  slots[slot].getData();
            slots[slot] = null;
        }else {
            HashNode node = slots[slot];
            node = node.getNext();
            HashNode prev = slots[slot];
            while (node != null && node.getKey() != key) {
                node = node.getNext();
                prev = prev.getNext();
            }
            val = (String) node.getData();
            prev.setNext(node.getNext());
        }
        num_items--;
        return val;
    }

    private int getSlot(String key){
        int slot = key.hashCode();
        slot = slot % slots.length;
        slot = Math.abs(slot);
        return slot;
    }
}
