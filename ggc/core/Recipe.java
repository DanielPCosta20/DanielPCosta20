package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Recipe implements Serializable {

    /** Serial number for serialization. */
	private static final long serialVersionUID = -9089804567903064714L;
    private double _alpha;
    private Set<Component> _components = new HashSet<>();;

    /**
     * 
     * @param alpha
     * @param componentes
     */
    public Recipe(double alpha, Set<Component> componentes){
        _alpha = alpha;
        _components = componentes;
    }

    /**
     * 
     * @return
     */
    public List<Component> getAllPComponents(){
        List<Component> list = new ArrayList<>(_components);
        return list;
    }

    /**
     * 
     * @return o agravamento da receita.
     */
    public double getAlpha(){
        return _alpha;
    }
    
    @Override
    public String toString() {
        String r = "";
        for (Component x: _components){
            r += x.toString(); 
        }
        return r.substring(0,r.length()-1);
    }
}
