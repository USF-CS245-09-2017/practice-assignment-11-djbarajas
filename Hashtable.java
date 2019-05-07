
public class Hashtable<k,v> {
    private HashNode<k,v>[] slots;
    private int num_items;
    private final double lambda = 0.75;
    /*
    *Hashtable()
    * creates a new hashtable with no items of a large size
    * NOTE: this implementation uses separate chaining
     */
    public Hashtable(){
        slots = new HashNode[2027];
        num_items = 0;
    }
    /*
    * containsKey()
    * calls the get function to see if the key exists
     */
    boolean containsKey(String key){
        String found = get(key);
        if(found == null)
            return false;
        else
            return true;
    }
    /*
    * get()
    * uses the hash function to get the correct bucket that the node is in
    * and sees if the string matches any node in that bucket
     */
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
    /*
    *put()
    * hashes the key to put in the correct bucket in the array
     */
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
    /*
    * grow()
    * creates a new array double the size of the original
    * then it loops through every element in the old one
    * finding all the nodes and rehashing them in the new array
    * then putting them in the new array at the new positions
     */
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

    /*
    * remove()
    * gets the node to remove and if the node has a next
    * value then it sets the previous to the remove's next
    * finally it returns the value
     */
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
    /*
    *getSlot()
    * this is my hash function for how i get the correct slot
     */
    private int getSlot(String key){
        int slot = key.hashCode();
        slot = slot % slots.length;
        slot = Math.abs(slot);
        return slot;
    }
}
