import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	static JFrame f = new JFrame();
	static BufferedReader br = null;
	static String currentLine = "";
	static double extX = 0;
	static double extY = 0;
	static double extZ = 0;
	
	static Polygon p = new Polygon();
	
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new FileReader(new File("trisection.gcode")));
		f.setSize(500,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.add(new Main());
		f.setVisible(true);
		
		f.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				try {
					currentLine = br.readLine();
				} catch (IOException e) {}
				f.repaint();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		while(true){
			
			try {
				currentLine = br.readLine();
			} catch (IOException e) {}
			f.repaint();
			
			try{
				Thread.sleep(16);
			}catch(Exception e){}
		}
	}
	
	public void paint(Graphics g){
		G1ToPositions(currentLine);
		g.drawString("X: " + extX, 20, 10);
		g.drawString("Y: " + extY, 20, 20);
		g.drawString("Z: " + extZ, 20, 30);
		g.drawString(currentLine, 20, 40);
		g.drawPolygon(p);
	}
	
	public void G1ToPositions(String line){
		int commentPos = line.indexOf(";");
		if(commentPos > -1){
			line.substring(0, commentPos);
		}
		if(line.startsWith("G1 ")){
			System.out.println("NEW G1 COMMAND FOUND");
			int end;
			int lx = line.indexOf("X");
			System.out.println("X: " + lx);
			if(lx > -1){
				System.out.println("NEW X FOUND");
				end = line.indexOf(" ", lx+1);
				if(end == -1){
					end = line.length();
				}
				extX = Double.parseDouble(line.substring(lx+1, end));
			}
			int ly = line.indexOf("Y");
			System.out.println("Y: " + ly);
			if(ly > -1){
				end = line.indexOf(" ", ly+1);
				if(end == -1){
					end = line.length();
				}
				extY = Double.parseDouble(line.substring(ly+1, end));
			}
			int lz = line.indexOf("Z");
			System.out.println("Z: " + lz);
			if(lz > -1){
				end = line.indexOf(" ", lz+1);
				if(end == -1){
					end = line.length();
				}
				String data = line.substring(lz+1, end);
				extZ = Double.parseDouble(data);
			}
			
		}
		
		p.addPoint((int)(extX*10), (int)(extY*10));
		
	}

}
