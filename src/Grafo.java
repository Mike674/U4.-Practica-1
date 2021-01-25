/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GX505DT
 */
public class Grafo {
    NodoGrafo ini, fin;
    
    public Grafo(){
        ini=fin=null;
    }
    
    
    public boolean insertarNodo(char v){
        NodoGrafo nuevo = new NodoGrafo(v);
        
        if(nuevo==null){
            return false;
        }
        
        if(ini==null && fin==null){
            ini = fin = nuevo;
            return true;
        }
        
        fin.sig=nuevo;
        nuevo.ant=fin;
        fin=nuevo;
        
        return true;
    }
    
    protected NodoGrafo buscarNodoGrafo(char valorBuscado) {
        NodoGrafo temp = ini;
        
        do{
            if(temp.valor==valorBuscado){
                return temp;
            }
            temp=temp.sig;
        } while(temp!=null);
        
        return temp;
    }
    
    public boolean eliminarNodo(char v){
        if(ini==null && fin==null){
            return false;
        }
        
        NodoGrafo nodoAEliminar = buscarNodoGrafo(v);
        if(nodoAEliminar == null){
            return false;
        }
        
        //verificar si es isla el nodo a eliminar
        //caso 1 (que no tenga aristas)
        if(nodoAEliminar.aristas!=null){
            return false;
        }
        
        //caso 2 (que otro nodo no tenga aristas a nodo eliminar)
        for(NodoGrafo temp = ini; temp != null; temp = temp.sig){
            if(encontrarAristas(temp, nodoAEliminar)==true){
                return false;
            }
        }
        
        //Eliminado si el NodoEliminar está en ini
        if(ini == fin && ini == nodoAEliminar){
            ini = fin = null;
            return true;
        }
        
        //Eliminado si el nodoEliminar está en fin
        if(ini == nodoAEliminar){
            NodoGrafo temp = ini.sig;
            ini.sig = null;
            temp.ant = null;
            ini = temp;
            return true;
        }
        
        //eliminado si el nodoEliminar está en medio
        if(fin == nodoAEliminar){
            NodoGrafo temp = fin.ant;
            temp.sig = null;
            fin.ant = null;
            fin = temp;
            return true;
        }
        
        nodoAEliminar.ant.sig=nodoAEliminar.sig;
        nodoAEliminar.sig.ant=nodoAEliminar.ant;
        nodoAEliminar.sig=nodoAEliminar.ant=null;
        return true;
    }
    
    public boolean insertarArista(char orig, char dest){
        NodoGrafo origen = buscarNodoGrafo(orig);
        
        if(origen==null){
            return false;
        }
        
        NodoGrafo destino = buscarNodoGrafo(dest);
        
        if(destino==null){
            return false;
        }
        
        NodoArista temp = new NodoArista(destino);
        
        if(temp==null){
            return false;
        }
        
        if(origen.aristas==null){
            origen.aristas=temp;
            return true;
        }
        
        //pongo a t2 en aristas(es decir al inicio de la lista de aristas)
        NodoArista t2 = origen.aristas;
        
        //este cicloavanza a t2, hasta que llega al último nodo de aristas
        while(t2.sig!=null){
            t2 = t2.sig;
        }
        
        t2.sig=temp;
        temp.ant=t2;
        
        return true;
    }
    
    private boolean encontrarAristas(NodoGrafo temp, NodoGrafo nodoAEliminar) {
        for(NodoArista temp2 = temp.aristas; temp2!=null; temp2 = temp2.sig){
            if(temp2.direccion==nodoAEliminar){
                return true;
            }
        }
        return false;
    }
    
    public boolean eliminarArista(char orig, char dest){
        NodoGrafo origen = buscarNodoGrafo(orig);
        
        if(origen == null){
            return false;
        }
        
        if(origen.aristas==null){
            return false;
        }
        
        NodoArista temp = origen.aristas;
        
        do{
            if(temp.direccion.valor==dest){
                if(temp==origen.aristas){
                    //revisando si la arista A ELIMINAR está como primer nodo
                    origen.aristas=temp.sig;
                    temp.direccion=null;
                    temp.sig=null;
                    if(origen.aristas!=null){
                        origen.aristas.ant=null;
                    }
                    return true;
                } else{
                    //revisar si la arista A ELIMINAR está como último nodo
                    if(temp.sig==null){
                        temp.ant.sig = null;
                        temp.direccion = null;
                        temp.ant = null;
                        return true;
                    }
                    //desconectar la arista A ELIMINAR estando en medio
                    temp.ant.sig = temp.sig;//el puntero sig de nodo arriba que estmp.ant, lo enlazo con nodo abajo que es temp.sig
                    temp.sig.ant = temp.ant;//el puntero ant de abajo que es temp.sig lo enlazo con nodo de arriba que es temp.ant
                    temp.sig = temp.ant = null;
                    temp.direccion = null;
                    return true;
                }
            }
            temp = temp.sig;
        } while(temp!=null);
        
        return false;
    }
    

}
