public class HashNode<String,Object> {
    private String key;
    private String value;
    private HashNode<String,String> next;
    /*
    getters and setters
     */
    public HashNode(String key,String data){
        this.key = key;
        this.value = data;
        next = null;
    }

    public String getData(){
        return value;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setData(String data){
        this.value = data;
    }

    public void setNext(HashNode<String,String> next){
        this.next = next;
    }

    public HashNode<String,String> getNext(){
        return next;
    }
}
