package Assignment2;

/**
 *
 * @author Hoshank Ailani
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.*;
import java.util.LinkedList;
import java.util.Queue;


public class Tree extends JFrame {
        SimplePanel buttonpanel = new SimplePanel();          //panel to store all buttons
        Border lineborder=new LineBorder(Color.BLACK,2);
        SimplePanel p2 = new SimplePanel();
        
        InputPanel inputpanel=new InputPanel();
        class SimplePanel extends JPanel{
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
            }
        }
        SimplePanel outputpanel=new SimplePanel();
        
     
        SimplePanel p5=new SimplePanel();
        JButton set=new JButton("Print the selected Order");
        String[] options={"Inorder","PreOrder","PostOrder"};
        JComboBox ordercombo=new JComboBox(options);
	JButton mirrorTree=new JButton("Mirror the Tree");
	JButton levelorder=new JButton("Level Order Traversal");
	JButton printmatrix=new JButton("Print Matrix");
	JButton clearinput=new JButton("Clear Input");
	JButton clearoutput=new JButton("Clear Output");
        JLabel label=new JLabel("Default");
        int radius=20;
        int num_of_vertices=0;
        int num_of_edges=0;
       int vstart,vend;
       int root;            //stores the root vertex
        int [][]vertcord=new int[50][2];      //Assuming maximum of 50 vertices drawn
        int [][]mirrorcord=new int [50][2];
        int [][]startcoord=new int[50][2];
        int [][]endcoord=new int[50][2];
        int [][]vmatrix=new int [50][50];
        int [][]relationmatrix=new int[50][2];      //stores left and right child of each node if it exists
   
  public Tree() {
  
   resetvmatrix();
      resetrmatrix();
    buttonpanel.setLayout(new GridLayout(2, 3));
    p2.setLayout(new BorderLayout());
    // Add buttons to the panel
    
	buttonpanel.setLayout(new GridLayout(1,3,10,10));
	buttonpanel.add(ordercombo);            //ComboBox for various Orders
        buttonpanel.add(set);                   //Selecting the comboBox Option
	buttonpanel.add(mirrorTree);
	buttonpanel.add(levelorder);
	buttonpanel.add(clearinput);
	buttonpanel.add(clearoutput);
	inputpanel.add(new JLabel("Input Area"));  
    outputpanel.setBorder(lineborder);
    outputpanel.setBackground(Color.WHITE);
    p2.setBackground(Color.BLACK);
    inputpanel.setBounds(0, 0, 600, 600);
    p2.add(inputpanel);
   outputpanel.add(new JLabel("Output Area"));
    outputpanel.setBounds(605, 0, 580, 600);
    buttonpanel.setBounds(400, 620, 400, 700);
    p2.add(outputpanel);
    
    p2.add(buttonpanel, BorderLayout.SOUTH);
    p2.add(p5,BorderLayout.CENTER);
    p2.setBackground(Color.WHITE);
    add(p2, BorderLayout.CENTER);
    
    inputpanel.addMouseListener(new MouseAdapter(){
             int startx,starty,endx,endy;
        
        @Override
        public void mouseReleased(MouseEvent e) {
           
               endx=e.getX()+10;
            endy=e.getY() +30;
            
            Graphics line=getGraphics();
            line.setColor(Color.red);
            
            if(startx!=endx&&starty!=endy&&(Math.abs(startx-endx)>=10) &&(Math.abs(endy-starty)>=10)){
                 line.drawLine(startx, starty, endx, endy);
                 endcoord[num_of_edges][0]=endx;
                 endcoord[num_of_edges][1]=endy;
                 
                 for(int i=0;i<num_of_vertices;i++){
                   
                     int dist2=calculatedistance(endx, endy, vertcord[i][0]+10,vertcord[i][1]+35);
                      int dist1=calculatedistance(startx, starty, vertcord[i][0]+10,vertcord[i][1]+35);
                     if(dist2<=radius+5){
                         vend=i;
                     }
                     if(dist1<=radius+5)
                         vstart=i;
                }
                 vmatrix[vstart][vend]=1;
                 num_of_edges++;
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            
            startx=e.getX() +10;
            starty=e.getY() +30;
            startcoord[num_of_edges][0]=startx;
            startcoord[num_of_edges][1]=starty;
                   
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            
            int x1=e.getX();
            int y1=e.getY();
            Graphics oval=getGraphics();
            oval.drawOval(x1 -5, y1 +5, 2*radius, 2*radius);
            oval.drawString(String.valueOf(num_of_vertices), x1+10, y1+25);
            vertcord[num_of_vertices][0]=x1;
            vertcord[num_of_vertices][1]=y1;
            mirrorcord[num_of_vertices][0]=x1;
            mirrorcord[num_of_vertices][1]=y1;
            num_of_vertices++;
          
        }
    });
    
    
   set.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
          if(ordercombo.getSelectedIndex()==0){ 
            Inorder();
              
          } 
          else if(ordercombo.getSelectedIndex()==1){
              
              //System.out.println("Preorder");
              Preorder();
          }
          else if(ordercombo.getSelectedIndex()==2){
              //Call postorder
              Postorder();
          }
          } 
});
    levelorder.addMouseListener(new MouseAdapter() {

       @Override
       public void mouseClicked(MouseEvent e) {
          levelorder();
       }
        
    });
    
    mirrorTree.addMouseListener(new MouseAdapter() {

       @Override
       public void mouseClicked(MouseEvent e) {
           mirror();
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
             //inputpanel.repaint();
              
             num_of_vertices=0;
             num_of_edges=0;
             resetrmatrix();
             resetvmatrix();
            
          }
        
    });
    
    clearoutput.addMouseListener(new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
             outputpanel.repaint();
          }
        
        });
  }

int calculatedistance(int x1,int y1,int x2,int y2){
        int distance;
        distance=(int)Math.sqrt(Math.pow(Math.abs(x1-x2),2)+Math.pow(Math.abs(y1-y2), 2));
        
        return distance;
    }

void findrelations(){
    
    for(int i=0;i<num_of_vertices;i++){
        for(int j=0;j<num_of_vertices;j++){
            if(vmatrix[i][j]==1){
                if(vertcord[j][0]<vertcord[i][0]&&(vertcord[j][1]>vertcord[i][1])){
                    
                    relationmatrix[i][0]=j;
                    //System.out.println(j + " is left child of " + i);
                }
                else if(vertcord[j][0]>vertcord[i][0]&&vertcord[j][1]>vertcord[i][1])
                    relationmatrix[i][1]=j;
                     //System.out.println("\n "+ j + " is right child of " + i);
                
                }
        }
    }
}
int findroot(){
    
    for(int j=0;j<num_of_vertices;j++){
        int count=0;
        for(int i=0;i<num_of_vertices;i++){
            if(vmatrix[i][j]==99)
                count++;
            
            if(count==num_of_vertices)
                return j;
        }
    }
    return 0;
}

int getLeft(int x){
    return relationmatrix[x][0];
}
int getRight(int x){
    return relationmatrix[x][1];
}

void Preorder(){
                findrelations();
              
              Graphics g=getGraphics();
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
              root=findroot();
              Thread t;
              //System.out.println(root);
              int node=root;
              Stack <Integer> stack=new Stack();
               stack.push(node);
               String sequence="Preorder sequence :";
              while (!stack.isEmpty())
        {
                   try {
                // thread to sleep for 1000 milliseconds
              
                int tempnode;
                while(!stack.isEmpty()){
                    Thread.sleep(1000);
                    tempnode=stack.lastElement();
                    //System.out.print("\t" + tempnode);
                    g.fillOval(vertcord[tempnode][0]+600,vertcord[tempnode][1]+2, 2*radius, 2*radius);
                    string.drawString(String.valueOf(tempnode),vertcord[tempnode][0]+615, vertcord[tempnode][1]+25);
                    
                    stack.pop();
                    if(getRight(tempnode)!=99)
                        stack.push(getRight(tempnode));         //Since we are using a Stack push right node before left
                    if(getLeft(tempnode)!=99)
                        stack.push(getLeft(tempnode));
                    sequence=sequence.concat(String.valueOf(tempnode) + ",");
                    message.drawString(sequence, 630, 620);
                }
  
          } catch (Exception ea) {
   System.out.println(ea);
   }
        }
              int startx,starty,endx,endy;
        for(int i=0;i<num_of_edges;i++){
            startx=startcoord[i][0];
            starty=startcoord[i][1];
            endx=endcoord[i][0];
            endy =endcoord[i][1];
            Graphics line=getGraphics();
            line.setColor(Color.red);
            line.drawLine(startx+600, starty, endx+600, endy);
        }
}

void mirror(){
  
   
    Graphics g=getGraphics();
    Graphics string=getGraphics();
    g.setColor(Color.RED);
    string.setColor(Color.WHITE);
    string.setFont(new Font("SansSerif",Font.BOLD , 14));
    int root=findroot();
    mirrorcord[root][0]=vertcord[root][0]+602;
    mirrorcord[root][1]=vertcord[root][1]+2;
   g.fillOval(vertcord[root][0]+590,vertcord[root][1]+2, 2*radius, 2*radius);
   string.drawString(String.valueOf(root),vertcord[root][0]+605, vertcord[root][1]+25);
  for(int i=0;i<num_of_vertices;i++){
        for(int j=0;j<num_of_vertices;j++){
            if(vmatrix[i][j]==1){
                if(vertcord[j][0]<vertcord[root][0]&&(vertcord[j][1]>vertcord[root][1])){
                    int xdifference=calculatedistance(vertcord[j][0], 0,vertcord[root][0],0);
                    mirrorcord[j][0]+=600+(2*xdifference);
                    g.fillOval(vertcord[j][0]+640+(2*xdifference),vertcord[j][1]+2, 2*radius, 2*radius);
                    string.drawString(String.valueOf(j),vertcord[j][0]+655+(2*xdifference), vertcord[j][1]+25);
                    mirrorcord[j][0]=vertcord[j][0]+655+(2*xdifference);
                    mirrorcord[j][1]=vertcord[j][1]+2;
                  
                }
                else if(vertcord[j][0]>vertcord[root][0]&&vertcord[j][1]>vertcord[root][1]){
                    int xdifference=calculatedistance(vertcord[j][0], 0,vertcord[root][0],0);
                    mirrorcord[j][0]+=600+(2*xdifference);
                   g.fillOval(vertcord[j][0]+640-(2*xdifference),vertcord[j][1]+2, 2*radius, 2*radius);
                    string.drawString(String.valueOf(j),vertcord[j][0]+655-(2*xdifference), vertcord[j][1]+25);
                     mirrorcord[j][0]=vertcord[j][0]+655-(2*xdifference);
                    mirrorcord[j][1]=vertcord[j][1]+2;
                    
                }
                    
                
                }
        }
    }
  
  for(int i=0;i<num_of_vertices;i++){
      for(int j=0;j<num_of_vertices;j++){
          if(vmatrix[i][j]==1){
             g.drawLine(mirrorcord[i][0], mirrorcord[i][1],mirrorcord[j][0],mirrorcord[j][1]);
          }
      }
  }
}
void Inorder(){
       findrelations();
              
              Graphics g=getGraphics();
              root=findroot();
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
              int node=root;
              Stack <Integer> stack=new Stack();
               String sequence="Inorder sequence :";
              while (!stack.isEmpty() || node != 99)
        {
                   try {
   
   Thread.sleep(1000);
   
           if(node==99){
           node=stack.pop();
           //System.out.print("\t" + node);
           g.fillOval(vertcord[node][0]+600,vertcord[node][1]+2, 2*radius, 2*radius);
           string.drawString(String.valueOf(node),vertcord[node][0]+615, vertcord[node][1]+25);
           
           sequence=sequence.concat(String.valueOf(node) + ",");
            message.drawString(sequence, 630, 620);
            node=getRight(node);   
           }
           if(node!=99){
               stack.push(node);
               node=getLeft(node);
               
           }
           
           
          } catch (Exception ea) {
   System.out.println(ea);
   }
        }
              int startx,starty,endx,endy;
        for(int i=0;i<num_of_edges;i++){
            startx=startcoord[i][0];
            starty=startcoord[i][1];
            endx=endcoord[i][0];
            endy =endcoord[i][1];
            Graphics line=getGraphics();
            line.setColor(Color.red);
            line.drawLine(startx+600, starty, endx+600, endy);
        }
}

void Postorder(){
    //System.out.println("Postrder");
              
               findrelations();
              
              Graphics g=getGraphics();
              root=findroot();
              g.setColor(Color.red);
              Graphics string=getGraphics();
              string.setColor(Color.WHITE);
              string.setFont(new Font("SansSerif",Font.BOLD , 14));
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
              
              int node=root;
              int current=node;
              int previous=99;
              Stack <Integer> stack=new Stack();
                stack.push(current);
                String sequence="PostOrder sequence :";
              while (!stack.isEmpty())
        {
                   try {
               
                while(!stack.isEmpty()){
                    
                    Thread.sleep(1000);
                    current=stack.lastElement();
                    if((previous==99)||(getLeft(previous)==current)||(getRight(previous)==current)){
                        if((getLeft(current))!=99){
                            stack.push(getLeft(current));
                           
                        }
                        else if((getRight(current))!=99){
                           
                            stack.push(getRight(current));
                        }
                        else{
                            g.fillOval(vertcord[current][0]+600,vertcord[current][1]+2, 2*radius, 2*radius);
                            string.drawString(String.valueOf(current),vertcord[current][0]+615, vertcord[current][1]+25);
                            sequence=sequence.concat(String.valueOf(current) + ",");
                            message.drawString(sequence, 630, 620);
                            stack.pop();
                        }
                    }
                    else if((getLeft(current))==previous){
                        if((getRight(current))!=99){
                            stack.push(getRight(current));
                        }
                        else{
                             g.fillOval(vertcord[current][0]+600,vertcord[current][1]+2, 2*radius, 2*radius);
                            string.drawString(String.valueOf(current),vertcord[current][0]+615, vertcord[current][1]+25);
                            sequence=sequence.concat(String.valueOf(current) + ",");
                            message.drawString(sequence, 630, 620);
                            stack.pop();
                        }
                    }
                    
                    else if((getRight(current))==previous){
                         g.fillOval(vertcord[current][0]+600,vertcord[current][1]+2, 2*radius, 2*radius);
                            string.drawString(String.valueOf(current),vertcord[current][0]+615, vertcord[current][1]+25);
                            sequence=sequence.concat(String.valueOf(current) + ",");
                            message.drawString(sequence, 630, 620);
                            stack.pop();
                    }
                    previous=current;
                
                    
                }
          } catch (Exception ea) {
   System.out.println(ea);
   }
        }
              int startx,starty,endx,endy;
        for(int i=0;i<num_of_edges;i++){
            startx=startcoord[i][0];
            starty=startcoord[i][1];
            endx=endcoord[i][0];
            endy =endcoord[i][1];
            Graphics line=getGraphics();
            line.setColor(Color.red);
            line.drawLine(startx+600, starty, endx+600, endy);
        }
}

void levelorder(){
    findrelations();
              
              Graphics g=getGraphics();
              Graphics string=getGraphics();
              root=findroot();
              Graphics message=getGraphics();
              message.setColor(Color.red);
              message.setFont(new Font("SansSerif",Font.BOLD , 16));
              
              int node=root;
              LinkedList<Integer> queue = new LinkedList<Integer>();
              queue.add(node);
              String sequence="Level Order Traversal";
              while (!queue.isEmpty())
        {
                   try {
            Thread.sleep(1000);
            node=queue.pollLast();
            g.setColor(Color.RED);
           g.fillOval(vertcord[node][0]+590,vertcord[node][1]+2, 2*radius, 2*radius);
           string.setColor(Color.WHITE);
           string.setFont(new Font("SansSerif",Font.BOLD , 14));
           string.drawString(String.valueOf(node),vertcord[node][0]+605, vertcord[node][1]+25);
           sequence=sequence.concat(String.valueOf(node) + ",");
           message.drawString(sequence, 630, 620);
           if(getLeft(node)!=99)
           queue.push(getLeft(node));
           if(getRight(node)!=99)
           queue.push(getRight(node));
           
          } catch (Exception ea) {
               System.out.println(ea);
                }
        }
        int startx,starty,endx,endy;
         for(int i=0;i<num_of_edges;i++){
            startx=startcoord[i][0];
            starty=startcoord[i][1];
            endx=endcoord[i][0];
            endy =endcoord[i][1];
            Graphics line=getGraphics();
            line.setColor(Color.red);
            line.drawLine(startx+600, starty, endx+600, endy);
        }
    
    
}

void resetvmatrix(){
    for(int i=0;i<50;i++){
          for(int j=0;j<50;j++){
              vmatrix[i][j]=99;  
          }
      }
}
void resetrmatrix(){
    for(int i=0;i<50;i++){
          for(int j=0;j<2;j++){
              relationmatrix[i][j]=99;
              startcoord[i][j]=0;
              endcoord[i][j]=0;
          }
      }
}

  public static void main(String[] args) {
    Tree frame = new Tree();
    frame.setTitle("Tree Operations");
    frame.setSize(1200, 670);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  
  }
}
