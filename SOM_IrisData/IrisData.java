import java.io.*;
import java.util.*;
import java.lang.Math;
public class IrisData {
  static final int  size_firstVect = 150 ;
  static final int  size_secondVect = 5 ;
  static final int  nb_value_vect = 4;

  /* cette methode permet d'allouer la mémoire pour les vecteur*/
  public static Vector<Vector<Object>> Allocation_vect(){
    Vector<Vector<Object>> vecteur = new Vector<Vector<Object>>() ;
    for (int i = 0; i < size_firstVect; i++) {
      vecteur.add(i, new Vector<Object>(size_secondVect));
    }
    return vecteur ;
} 
  /* cette methode permet de lire le fichier irisData & le remplissage de vecteur */
  public static Vector<Vector<Object>> Readfile() {
    Vector<Vector<Object>> vect = Allocation_vect();
    String file = "iris.data";
    String ligne ;
    try{
      BufferedReader br = new BufferedReader(new FileReader(file));
      int i=0 ;    // incrémenter le grand vect
      while((ligne = br.readLine()) != null ){
        String [] data = ligne.split(","); 
        for(int j = 0 ;j < size_secondVect; j++){
          if(j < nb_value_vect){
            vect.get(i).add(j,Double.parseDouble(data[j])); 
          }
          else {
            vect.get(i).add(j,data[nb_value_vect]);         
          }
        }
        i++ ; 
      }
      br.close();
      }catch (Exception e) {
        e.printStackTrace();
    }
    return vect ;
  }
  /* cette methode permet d'afficher le vecteur irisData */
  public static void Display_vect(Vector<Vector<Object>> vect){
    for (int i = 0; i < size_firstVect; i++) {
      System.out.println("vecteur "+i);
      for (int j = 0; j < size_secondVect; j++) {
             System.out.print(vect.get(i).get(j)+" ");   
         } 
         System.out.println("\n");         
       }
  }
  /* cette méthode permet de normaliser les vecteurs de données */
  public static void Normalisation_vect(Vector<Vector<Object>> vect ){
    int i,j,k;
    double norm;
    for(i = 0;i < size_firstVect; i++){
      norm = 0.0 ;
      for(j = 0;j < nb_value_vect; j++){
         norm += Math.pow((Double) vect.get(i).get(j),2);
      }
      norm = Math.sqrt(norm);
      for(k = 0;k < nb_value_vect;k++){
        vect.get(i).set(k, (Double)vect.get(i).get(k) / norm);
      }
    }
  }
  /* cette methode permet de calculer la moyenne de vecteurs de donneés par colonne (selon mon imagination) 
  j'auris un tableau de 4 valeurs*/
  public static ArrayList<Double> Average(Vector<Vector<Object>> vect){
    int i,j ; 
    ArrayList<Double> tab = new ArrayList<Double>(nb_value_vect);
    double sum ;
    //on accède aux valeurs de mon deuxieme vecteur
    for(i = 0;i < nb_value_vect; i++){
      sum = 0.0;
      // récupérer toute les valeurs col par col de mon deuxieme vecteur
      for(j = 0;j < vect.size(); j++){
        sum += (Double) vect.get(j).get(i);
      }
      tab.add(i,sum / vect.size());
    }
    return  tab ; 
  }
  
  public static void main(String[] args){
    Vector<Vector<Object>> vect ;
    vect = Allocation_vect();
    vect = Readfile();
    //System.out.println(vect);
    //Display_vect(vect);
    Normalisation_vect(vect);
    Display_vect(vect);
    /* ArrayList<Double> tab ;
    tab=Average(vect);
    System.out.println("tab_moy : "+tab); */
  }
}
