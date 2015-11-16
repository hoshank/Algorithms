package Assignment2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;             //For DFS and D-search
import javax.swing.*;
import javax.swing.border.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;             //For BFS


public class Graph extends JFrame {
        SimplePanel buttonpanel = new SimplePanel();          //panel to store all buttons
        Border lineborder=new LineBorder(Color.BLACK,2);
        SimplePanel p2 = new SimplePanel();
        InputPanel inputpanel=new InputPanel();
        
        class SimplePanel extends JPanel{               //Custom Panel class
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
            }
        }
        class InputPanel extends JPanel{
            
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                setBorder(lineborder);
                setBackground(Color.WHITE);
                //
            }
        }
        SimplePanel outputpanel=new SimplePanel();
        
     
        SimplePanel p5=new SimplePanel();
        JButton bfs=new JButton("BFS");
	JButton dfs=new JButton("DFS");
	JButton dsearch=new JButton("D-search");
	JButton clearinput=new JButton("Clear Input");
	JButton clearoutput=new JButton("Clear Output");
        JButton selectstart=new JButton("Select Start node");
        int radius=20;          //radius of circle to represent nodes
        int num_of_vertices=0;
        int num_of_edges=0;
        int vstart,vend;                        //TO determine starting and ending vertex while making an edge
        int root;                               //stores the root vertex
        int [][]vertcord=new int[50][2];      
        int [][]startcoord=new int[50][2];
        int [][]endcoord=new int[50][2];
        int [][]vmatrix=new int [50][50];     
        String sequence;
        Queue<Integer> queue;
         LinkedList<Integer> adj[];
  public Graph() {
  
       resetvmatrix();
     
      queue=new LinkedList<Integer>();
    buttonpanel.setLayout(new GridLayout(2, 3));
    p2.setLayout(new BorderLayout());
    // Add buttons to the panel
    
	buttonpanel.setLayout(new GridLayout(2,3,10,10));
	buttonpanel.add(bfs);
        buttonpanel.add(dfs);
	buttonpanel.add(dsearch);
	buttonpanel.add(clearinput);
	buttonpanel.add(clearoutput);
	inputpanel.add(new JLabel("Input Area"));
 
        
    outputpanel.setBorder(lineborder);
  
    outputpanel.setBackground(Color.WHITE);

    inputpanel.setBounds(0, 0, 600, 600);
    p2.add(inputpanel);
   outputpanel.add(new JLabel("Output Area"));
    outputpanel.setBounds(605, 0, 600, 600);
    p2.add(outputpanel);
    
    p2.add(buttonpanel, BorderLayout.SOUTH);
    p2.add(p5,BorderLayout.CENTER);
    p2.setBackground(Color.WHITE);
    add(p2, BorderLayout.CENTER);
    sequence=new String("DFS Sequence : ");
    inputpanel.addMouseListener(new MouseAdapter(){
             int startx,starty,endx,endy;
        
        @Override
        public void mouseReleased(MouseEvent e) {
           
            endx=e.getX()+10;               //Get coordinates where ouse was released
            endy=e.getY() +30;
            
            Graphics line=getGraphics();
            line.setColor(Color.red);
            
            if((Math.abs(startx-endx)>=10) &&(Math.abs(endy-starty)>=10)){      //To avoid accidental edges
                 line.drawLine(startx, starty, endx, endy);
                 endcoord[num_of_edges][0]=endx;
                 endcoord[num_of_edges][1]=endy;
                 
                 for(int i=0;i<num_of_vertices;i++){                //Determine the start and ending edges
                   
                     int dist2=calculatedistance(endx, endy, vertcord[i][0]+10,vertcord[i][1]+35);
                      int dist1=calculatedistance(startx, starty, vertcord[i][0]+10,vertcord[i][1]+35);
                     if(dist2<=radius+5){
                         vend=i;
                     }
                     if(dist1<=radius+5)
                         vstart=i;
                }
                 vmatrix[vstart][vend]=1;
                 vmatrix[vend][vstart]=1;
                 num_of_edges++;
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            
            startx=e.getX() +10;                //Get the coordinates where mouse was pressed
            starty=e.getY() +30;
            startcoord[num_of_edges][0]=startx;
            startcoord[num_of_edges][1]=starty;
                   
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            
            int x1=e.getX();
            int y1=e.getY();
            Graphics oval=getGraphics();
            oval.drawOval(x1 -5, y1 +5, 2*radius, 2*radius);                //Drawing a vertex
            oval.drawString(String.valueOf(num_of_vertices), x1+10, y1+25);
            vertcord[num_of_vertices][0]=x1;
            vertcord[num_of_vertices][1]=y1;
            num_of_vertices++;
          
        }
    }); 

    clearinput.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
             
            outputpanel.repaint();
             p2.repaint();
             p5.repaint();
             buttonpanel.repaint();
             inputpanel.repaint();
             num_of_vertices=0;
             num_of_edges=0;
             resetvmatrix(); 
          } 
    });
    
    clearoutput.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
              outputpanel.repaint();
          }
        
        });
    
    bfs.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
              String s = JOptionPane.showInputDialog(null, "Enter the source vertex") ;
            int source = Integer.parseInt(s) ;

             bfs(source);
          } 
    });
    
    dfs.addMouseListener(new MouseAdapter() {

           @Override
           public void mouseClicked(MouseEvent e) {
               String s = JOptionPane.showInputDialog(null, "Enter the source vertex") ;
            int source = Integer.parseInt(s) ;

             dfs(source);
           }
        
    });
    
    dsearch.addMouseListener(new MouseAdapter() {
         @Override
           public void mouseClicked(MouseEvent e) {
               String s = JOptionPane.showInputDialog(null, "Enter the source vertex") ;
            int source = Integer.parseInt(s) ;

             dsearch(source);
           }
});
  }

int calculatedistance(int x1,int y1,int x2,int y2){
        int distance;
        distance=(int)Math.sqrt(Math.pow(Math.abs(x1-x2),2)+Math.pow(Math.abs(y1-y2), 2));
        
        return distance;
    }
void bfs(int source)
    {
         LinkedList<Integer> adj[];
         adj = new LinkedList[num_of_vertices];
         
        for (int i=0; i<num_of_vertices; ++i){
            adj[i] = new LinkedList();
    }
        
        for(int i=0;i<num_of_vertices;i++){
            for(int j=0;j<num_of_vertices;j++){
                if(vmatrix[i][j]!=99){
                    adj[i].add(j);
                }
            }
        }
        
         boolean visited[] = new boolean[num_of_vertices];
 
        LinkedList<Integer> queue = new LinkedList<Integer>();
 
        visited[source]=true;
        queue.add(source);
        int previous=99;
         int startx,starty,endx,endy;
              Graphics g=getGraphics();
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
          g.fillOval(vertcord[source][0]+600,vertcord[source][1]+2, 2*radius, 2*radius);
          string.drawString(String.valueOf(source),vertcord[source][0]+615, vertcord[source][1]+25);
          String sequence="BFS sequence :" + source;
          //System.out.println(sequence);
        while (queue.size() != 0)
        {
            source = queue.poll();
           
            Iterator<Integer> i = adj[source].listIterator();
            
            while (i.hasNext())
            {
                try {
                Thread.sleep(1000);
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    
                    previous=n;
                    if(previous!=source){
                    startx=vertcord[source][0];
                    starty=vertcord[source][1];
                    endx=vertcord[previous][0];
                    endy =vertcord[previous][1];
                    Graphics line=getGraphics();
                    line.setColor(Color.red);
                    line.drawLine(startx+610, starty+25, endx+610, endy+35);
                    
                    g.fillOval(vertcord[n][0]+600,vertcord[n][1]+2, 2*radius, 2*radius);
                    string.drawString(String.valueOf(n),vertcord[n][0]+615, vertcord[n][1]+25);
                    sequence=sequence.concat(",");
                    sequence=sequence.concat(String.valueOf(n));
                    }
                    queue.add(n);
                    message.drawString(sequence, 630, 620);
                    
                }
                   }catch (Exception ea) {
   System.out.println(ea);
            }
            } 
   }
         
        }
      

void dsearch(int source){
     LinkedList<Integer> adj[];
         adj = new LinkedList[num_of_vertices];
        for (int i=0; i<num_of_vertices; ++i){
            adj[i] = new LinkedList();
    }
        
        for(int i=0;i<num_of_vertices;i++){
            for(int j=0;j<num_of_vertices;j++){
                if(vmatrix[i][j]!=99){
                    adj[i].add(j);
                }
            }
        }
        
         boolean visited[] = new boolean[num_of_vertices];

        Stack<Integer> stack = new Stack<Integer>();

        visited[source]=true;
        stack.push(source);
        int previous=99;
         int startx,starty,endx,endy;
              Graphics g=getGraphics();
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
          g.fillOval(vertcord[source][0]+600,vertcord[source][1]+2, 2*radius, 2*radius);
          string.drawString(String.valueOf(source),vertcord[source][0]+615, vertcord[source][1]+25);
          String sequence="D-Search sequence :" + source;
            g.fillOval(vertcord[source][0]+600,vertcord[source][1]+2, 2*radius, 2*radius);
          string.drawString(String.valueOf(source),vertcord[source][0]+615, vertcord[source][1]+25);
        while (!stack.isEmpty())
        {
            source = stack.pop();
            //System.out.print(source+" ");
           
            Iterator<Integer> i = adj[source].listIterator();
            
            while (i.hasNext())
            {
                try {
                Thread.sleep(1000);
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    
                    previous=n;
                    if(previous!=source){
                    startx=vertcord[source][0];
                    starty=vertcord[source][1];
                    endx=vertcord[previous][0];
                    endy =vertcord[previous][1];
                    Graphics line=getGraphics();
                    line.setColor(Color.red);
                    line.drawLine(startx+610, starty+25, endx+610, endy+35);
                    
                    g.fillOval(vertcord[n][0]+600,vertcord[n][1]+2, 2*radius, 2*radius);
                    string.drawString(String.valueOf(n),vertcord[n][0]+615, vertcord[n][1]+25);
                    sequence=sequence.concat(",");
                    sequence=sequence.concat(String.valueOf(n));
                    message.drawString(sequence, 630, 620);
                    }
                    stack.push(n);
                }
                   }catch (Exception ea) {
                    System.out.println(ea);
                    }
            } 
   }
}
void resetvmatrix(){
    for(int i=0;i<50;i++){
          for(int j=0;j<50;j++){
              vmatrix[i][j]=99;  
          }
      }
}

void dfs(int source){
   
         adj = new LinkedList[num_of_vertices];
        for (int i=0; i<num_of_vertices; ++i){
            adj[i] = new LinkedList();
    }
        
        for(int i=0;i<num_of_vertices;i++){
            for(int j=0;j<num_of_vertices;j++){
                if(vmatrix[i][j]!=99){
                    adj[i].add(j);
                }
            }
        }
        
         boolean visited[] = new boolean[num_of_vertices];
            int previous =99;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[source]=true;
        previous=DFSUtililty(source, visited,previous);
         
}
 int DFSUtililty(int v,boolean visited[],int previous)
    {
        
        visited[v] = true;
        //System.out.print(v+" ");
         Graphics g=getGraphics();
         
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
            g.fillOval(vertcord[v][0]+600,vertcord[v][1]+2, 2*radius, 2*radius);
           string.drawString(String.valueOf(v),vertcord[v][0]+615, vertcord[v][1]+25);
             
                    sequence=sequence.concat(String.valueOf(v) + " , ");
                   message.drawString(sequence, 630, 620);   
                    
        if(previous!=99){
            int startx,starty,endx,endy;
                     startx=vertcord[v][0];
                    starty=vertcord[v][1];
                    endx=vertcord[previous][0];
                    endy =vertcord[previous][1];
                    Graphics line=getGraphics();
                    line.setColor(Color.red);
                    line.drawLine(startx+610, starty+25, endx+610, endy+35);
                     
        }
        Iterator<Integer> i = adj[v].listIterator();
        try {
                Thread.sleep(1000);
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n]){
                
                DFSUtililty(n, visited,v);
                
            }
        }
        } catch (Exception ea) {
   System.out.println(ea);
   }
         
        return v;
    }

  public static void main(String[] args) {
    Graph frame = new Graph();
    frame.setTitle("Graph Operations");
    frame.setSize(1220, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true); 
  }
}

