package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Test {        

private double Pierrot;
private int Jester;
private boolean Harlequin;
private float Columbina;
private double Freak;
private double result;

public Test(double Pierrot, int Jester, boolean Harlequin, float Columbina){

this.Pierrot = Pierrot;
this.Jester = Jester;
this.Harlequin = Harlequin;
this.Columbina = Columbina;

SmartDashboard.putNumber("Freak", Freak);

}

public double Freak(){

if(Harlequin == false){

    Pierrot *= 2;
    Columbina += 2;

}

if (Harlequin == true){

    Freak -= Pierrot;
    Freak /= Columbina;
}

Freak *= Jester; 
Freak /= Columbina + Jester;
Freak *= Pierrot * Columbina;

result = Freak;


return result;

}

}