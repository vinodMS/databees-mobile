package nl.isld.databees;

import nl.isld.databeess.disease.*;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DiseaseFragment extends Fragment implements OnClickListener{

    ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
	
	public DiseaseFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);
        
        btn1 = (ImageButton) rootView.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (ImageButton) rootView.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (ImageButton) rootView.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (ImageButton) rootView.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (ImageButton) rootView.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (ImageButton) rootView.findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (ImageButton) rootView.findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (ImageButton) rootView.findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (ImageButton) rootView.findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        
        
        return rootView;
    }
	
    public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = null;
        switch (v.getId()){
            case R.id.btn1:
            	fragment = new Varroa();
            	fragmentManager.beginTransaction()
    				.replace(R.id.frame_container, fragment)
    				.addToBackStack(null)
    				.commit();
    			break;
            case R.id.btn2:
            	fragment = new European();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();         
    			break;
            case R.id.btn3:
            	fragment = new American();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();               
    			break;
            case R.id.btn4:
            	fragment = new Nosema();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();            
    			break;
            case R.id.btn5:
            	fragment = new Chalkbrood();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();              
    			break;
            case R.id.btn6:
            	fragment = new Wax();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();                
    			break;
            case R.id.btn7:
            	fragment = new Tracheal();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();               
    			break;
            case R.id.btn8:
            	fragment = new Viruses();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();                
    			break;
            case R.id.btn9:
            	fragment = new Small();
            	fragmentManager.beginTransaction()
            		.addToBackStack(null)
    				.replace(R.id.frame_container, fragment).commit();     
    			break;
        }
    
	}
	
}
