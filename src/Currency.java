public class Currency {
private String name;
private int amount = 0;
private int superamt = 0;
public void add(int amount) {
	this.amount = this.amount +amount;
	superamt = superamt + amount;
}
public void subtract(int amount){
	this.amount = this.amount - amount;
}
public Currency(String name) {
	this.name = name;
}
public Currency(){
name = null;
}
public int getSuperAmt(){
	return superamt;
}
public int getAmt(){
	return amount;
}

}
