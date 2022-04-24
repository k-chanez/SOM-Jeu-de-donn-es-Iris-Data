import java.util.*;

public class Noeud extends IrisData {
    static final int rowMap = 6;
    static final int colMap = 10 ;
    static final int size_listBmu = 2 ;
    static final int first_iteration = 500 ;
    static final int second_iteration = 1500 ;
    static final double first_alpha_init = 0.7 ;
    static final double second_alpha_init = first_alpha_init/ 10.0 ;
    static final int first_rayon =3 ;
    static final int second_rayon =1 ;
    
    public static double Random(double min, double max){
        Random random = new Random();
        double value;
        value =  min + (max - min) * random.nextDouble();
        return value; 
    }
    /* cette methode permet d'allouer la mémoire pour la map (réseau de neuronnes) sous forme d'une 
    ArrayList de Arraylist de vecteur d'objet*/
    public static ArrayList<ArrayList<Vector<Object>>> allocation_map(int row,int col){
        int i,j;
        ArrayList<ArrayList<Vector<Object>>> map = new ArrayList<ArrayList<Vector<Object>>>(row);
        for (i = 0; i < row; i++) {
            map.add(new ArrayList<Vector<Object>>(col));
            for (j = 0; j < col ; j++){
                map.get(i).add(j,new Vector<Object>(size_secondVect));
            }
        }
        return 
        map;
    } 
    /* cette methode permet de remplir la map avec les valeurs générées aleatoirement entre   
    la moyenne calculée dans la classe IrisData et l'intervalle donné en cours */
    public static void Remplissage(ArrayList<ArrayList<Vector<Object>>> map, Vector<Vector<Object>> Vect){
        int i,j,index_Map;
        ArrayList<Double> Vect_moy = Average(Vect);
        for (i = 0; i < map.size(); i++){
            for (j = 0; j < map.get(i).size(); j++){	
                for (index_Map = 0; index_Map < size_secondVect; index_Map++){
                    if (index_Map < nb_value_vect) {
                        map.get(i).get(j).add(index_Map, Random(Vect_moy.get(index_Map)-0.0005,Vect_moy.get(index_Map)+ 0.0005));
                    }
                    else{
                        map.get(i).get(j).add(index_Map,"X");
                    }
                }
            }
        }
    }
    /* cette methode permet d'afficher la map (réseau de neuronnes) */
    public static void Display_map(ArrayList<ArrayList<Vector<Object>>> map){
        int i,j,index_Map;
        for (i = 0; i < map.size(); i++) {
            for (j = 0; j < map.get(i).size() ; j++) {
                for (index_Map = 0; index_Map < size_secondVect; index_Map++) {
                    System.out.println(map.get(i).get(j).get(index_Map));
                }
               System.out.println(); 
            }
            System.out.println();
    }

    }
    /* cette methode permet de creer une Arraylist d'indice */
    public static ArrayList<Integer> creat_tab(int size){
        ArrayList<Integer> tab = new ArrayList<Integer> ();
        for (int i = 0; i < size; i++) {
           tab.add(i);
        }
        return tab;
    } 
    /* cette methode permet de shuffler les indices de tableau */
    public static void shuffle(ArrayList<Integer> tab ){
        Collections.shuffle(tab);   
    }
    /* cette methode permet d'allouer la memoire pour la liste des bmu à deux dimension */
    public static ArrayList<ArrayList<Integer>> Init_liste(int size){
        int i;
        ArrayList<ArrayList<Integer>> liste=new ArrayList<ArrayList<Integer>>(size);
        for (i = 0; i < size; i++) {
            liste.add(i,new ArrayList<Integer>());         
        }
        return liste ;
    }
    /* cette methode permet de supprimer les élements de la liste à deux dimension */
    public static void remove_list(ArrayList<ArrayList<Integer>> liste){
        int i ;
        int col= liste.get(0).size();
        for (i = 0; i < col; i++) {
            liste.get(0).remove(0);   
            liste.get(1).remove(0);      
        }         
    } 

    public static void test_remove_liste(){
        ArrayList<ArrayList<Integer>> liste = Init_liste(size_listBmu);      
        for(int i=0;i < 5;i++){
            liste.get(0).add(i,i*2);
            liste.get(1).add(i,i+2);
        }
        System.out.println("avant la suppression :"+liste);
        remove_list(liste);
        System.out.println("apres la suppression :"+liste);
    } 
    /* cette methode permet de chosir aleatoirement un bmu  */
    public static ArrayList<ArrayList<Integer>> Bmu_random(ArrayList<ArrayList<Integer>> liste){
        int col = liste.get(0).size();
        Random random =new Random();
        ArrayList<ArrayList<Integer>> copy_liste = Init_liste(2);// copy_list pour génerer un seul bmu 
        int index = random.nextInt(col); // génerer une col aleatoirement 
        copy_liste.get(0).add(0,liste.get(0).get(index));
        copy_liste.get(1).add(0,liste.get(1).get(index));
        
        return copy_liste ; 
    }
    /* cette methode permet choisir un bmu aleatoirement et elle récupère l'etiquette de vecteur 
    d'iris data en le mettant dans le vecteur de la map a l'aide des indices de bmu trouvé
    ensuite elle calcule les extrimités de la nouvelle map (voisinage) 
    aprés elle parcourt la nouvelle map trouvée en mettre à jour les poids de vecteur de cette map 
    appliquant la formule citée en cours */
    public static void Voisinage(ArrayList<ArrayList<Vector<Object>>> map,Vector<Vector<Object>> vect,ArrayList<ArrayList<Integer>> liste,ArrayList<Integer> tab,int rayon,double alpha,int RowVect){
        int extrimite_begin_col,extrimite_begin_row,extrimite_end_col, extrimite_end_row ;
        int index_vect ;
        int New_ligne_map,New_col_map;
        // on fait appelle à la fonction bmu si on a plus de deux bmu on choisira un seul aleatoirement 
        if(liste.get(0).size() > 1){
            liste = Bmu_random(liste);  
        }
         //etiquage 
         map.get(liste.get(0).get(0)).get(liste.get(1).get(0)).set(4,vect.get(tab.get(RowVect)).get(4));
        
        if(liste.get(0).get(0) - rayon < 0){
            extrimite_begin_row = 0;
        }else{
            extrimite_begin_row = liste.get(0).get(0) - rayon;
        }
        if (liste.get(0).get(0) + rayon < map.size()){
            extrimite_end_row = liste.get(0).get(0) + rayon ;
        }else{
            extrimite_end_row = map.size() - 1;
        }
        // limiter les collones 
        if(liste.get(1).get(0) - rayon < 0){
            extrimite_begin_col = 0 ;
        }else {
            extrimite_begin_col = liste.get(1).get(0) - rayon ;
        }
        if(liste.get(1).get(0) + rayon < map.get(0).size()){
            extrimite_end_col =  liste.get(1).get(0) + rayon ;
        }else{	
            extrimite_end_col = map.get(0).size() - 1;
        }
        // UPDATES 
        for(New_ligne_map = extrimite_begin_row; New_ligne_map <= extrimite_end_row; New_ligne_map++){
            for(New_col_map = extrimite_begin_col; New_col_map <= extrimite_end_col; New_col_map++){
                for(index_vect = 0; index_vect < nb_value_vect ; index_vect++){
                    // Wij= les poids de vecteur de la map + alpha * vect avec les indices shuffler - les poids de vecteur de la map
                    map.get(New_ligne_map).get(New_col_map).set(index_vect, (Double)map.get(New_ligne_map).get(New_col_map).get(index_vect) + alpha * ((Double)vect.get(tab.get(RowVect)).get(index_vect) - (Double)map.get(New_ligne_map).get(New_col_map).get(index_vect)));
                }
            }
        } 
        
    }
    /* cette methode permet de chercher le bmu en calculant la distance euclidienne entre le vecteur de la map
    et le vecteur de donnée irisData avec les indices shuffler 
    une fois qu'elle a trouvé le bmu(les indices) elle le stocke dans la liste à deux dimension
    ensuite elle fait appel à la methode voisinage d'ou la methode de voisinage prend le alpha calculé avec la formule donnée
    en cours et tout ça se fait en  deux phases d'iteration en diminuant le rayon avec la formule citée en 
    cours */
    public static void Apprentissage(ArrayList<ArrayList<Vector<Object>>> map,Vector<Vector<Object>> vect,double alpha_init,int rayon,int nb_iteration){
        int t,RowVect ,MapRow,MapCol; 
        double dis_minimal,distance = 0.0;
        int index_vect; 
        int Drayon = nb_iteration / rayon ; // 500/3 = 166
        double alpha ;
        ArrayList<Integer> tab = creat_tab(size_firstVect);
        ArrayList<ArrayList<Integer>> liste = Init_liste(size_listBmu); 
        for (t = 0; t < nb_iteration; t++) {
            shuffle(tab); 
            alpha = alpha_init * (1.0 - ((double) t / (double) nb_iteration));
            if((t % Drayon == 0) && (rayon > 1)){
                rayon -= 1;
            }
            // chaque indice de mon grand vecteur il parcourt toute la map en cherchant le bmu
            for (RowVect = 0; RowVect < size_firstVect;RowVect++){
                dis_minimal = 200.0;
                for (MapRow = 0; MapRow < map.size(); MapRow++){
                    for (MapCol = 0; MapCol < map.get(MapRow).size(); MapCol++){
                        distance = 0.0;
                        for (index_vect = 0; index_vect < nb_value_vect; index_vect++) {
                            /* calcul de la distance euclidienne avec les poids de vecteur de la map et les poids de mon vecteur irisdata 
                                shuffler*/
                            distance += Math.pow((double) map.get(MapRow).get(MapCol).get(index_vect)- (double)vect.get(tab.get(RowVect)).get(index_vect),2) ;
                        }
                        
                        distance = Math.sqrt(distance); 
                        if (distance <= dis_minimal){
                            if(distance < dis_minimal){
                                remove_list(liste); // liste vide en premiere iteration puis suprimme la liste a chaque fois qu'on trouve dist_min  
                                liste.get(0).add(0,MapRow);//  stocker indice de ligne de vecteur de la map 
                                liste.get(1).add(0,MapCol);//  stocker indice de colone de vect de map
                            }
                            
                            if(distance == dis_minimal){
                                for (int i = 1; i < liste.size(); i++) { 
                                    liste.get(0).add(i, MapRow);//stocker les bmu dans la liste l'un a cote de l'autre sinon ça sera ecrasé
                                    liste.get(1).add(i,MapCol); 
                                } 
                            }

                            dis_minimal = distance ;
                        }
                    
                    }   
                }
                Voisinage(map,vect, liste,tab, rayon,alpha,RowVect);
              
            }        
        }
    }
    /* cette methode permet d'afficher les etiquettes de la map pour voir le resultat de
     l'apprentissage (clustering)*/
    public static void affichage_etiquette(ArrayList<ArrayList<Vector<Object>>> map,Vector<Vector<Object>> vect){
       int mapRow ,mapCol;
       System.out.println();
        for( mapRow = 0; mapRow < map.size(); mapRow++) {
            for (mapCol = 0; mapCol < map.get(mapRow).size(); mapCol++) {
            
                 switch((String)map.get(mapRow).get(mapCol).get(4)){

                    case "Iris-setosa":
                        System.out.print("\033[32m SE\033[0m");
                        break;   
                    case "Iris-versicolor":
                        System.out.print("\033[31m VE\033[0m");
                        break; 
                    case "Iris-virginica":
                        System.out.print("\033[34m VI\033[0m");
                        break; 
                    }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("\033[32m SE : Iris-setosa \033[0m");
        System.out.println("\033[31m VE : Iris-versicolor \033[0m");
        System.out.println("\033[34m VI : Iris-virginica \033[0m");
    }
    public static void main(String[] args){
        Vector<Vector<Object>> Vect;
        ArrayList<ArrayList<Vector<Object>>> Map;  
        Vect=Readfile();
        Normalisation_vect(Vect);
        Map = allocation_map(rowMap,colMap);
        Remplissage(Map,Vect);
        //Display_map(Map); 
        //test_remove_liste();
        Apprentissage(Map,Vect,first_alpha_init,first_rayon,first_iteration);
        Apprentissage(Map,Vect,second_alpha_init,second_rayon,second_iteration);
        affichage_etiquette(Map, Vect);
    }
}

