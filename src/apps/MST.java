package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {/*COMPLETE THIS METHOD*/
		PartialTreeList L = new PartialTreeList();
		for(int i = 0; i<graph.vertices.length; i++){
			Vertex v = graph.vertices[i];
			PartialTree T = new PartialTree(v);
			while(v.neighbors!=null){
				PartialTree.Arc arc = new PartialTree.Arc(v, v.neighbors.vertex, v.neighbors.weight);
				//System.out.println(arc.toString());
				//System.out.println(arc);
				T.getArcs().insert(arc);
				v.neighbors = v.neighbors.next;
			}
			//System.out.println(T.toString());
			L.append(T);
		}
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> arcs = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() > 1){
			PartialTree PTX = ptlist.remove();
			PartialTree.Arc PQX = PTX.getArcs().deleteMin();//step4
			Vertex v1 = PTX.getRoot();
			Vertex v2 = PQX.v2;
			while(PTX.getArcs().size() > 0){
				if(v1.equals(v2)){//repeat step4
					PQX = PTX.getArcs().deleteMin();
					v1 = PTX.getRoot();
					v2 = PQX.v2;
				}
				else break;
			}
			System.out.println(PQX+ " is a component of ");
			PartialTree PTY = ptlist.removeTreeContaining(PQX.v2);
			PTX.merge(PTY);
			arcs.add(PQX);
			ptlist.append(PTX);
		}
		return arcs;
	}
}