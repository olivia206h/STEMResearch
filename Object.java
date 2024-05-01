public class Object {
  int value; 
  int weight;
  String name;

  public Object(int value, int weight, String name) {
    this.value = value;
    this.weight = weight;
    this.name = name;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
