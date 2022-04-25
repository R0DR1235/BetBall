import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class GraphicInterface {
	private JFrame mainPage;
	private JFrame results;
	private JFrame moreInformation;
	private JFrame classification;
	private JFrame bets;
	private static int day=1;
	private static Font font = new Font("Areal", Font.BOLD, 12);
	private static Font font2 = new Font("Areal", Font.BOLD, 13);
	JLabel balanceText;
	private static double balance=300;
	private Team team1;private Team team2;private Team team3;private Team team4;
	private Team team5;private Team team6;private Team team7;private Team team8;
	private ArrayList<Team> teams;
	private DefaultListModel listGames;
	private DefaultListModel listInformation;
	private JLabel game1;
	private JLabel game2;
	private JLabel game3;
	private String data[][]= new String[6][4];
	private JComboBox game1Bet;
	private JComboBox game2Bet;
	private JComboBox game3Bet;
	private String bet1="";
	private String bet2="";
	private String bet3="";
	private JTextField game1Money;
	private JTextField game2Money;
	private JTextField game3Money;
	private JTextArea betsText;
	private JTextArea endText;
	private static final Color red= new Color(159,32,32);
	private boolean doneBet=false;

	//INTERFACE GRAFICA(INICIALIZAÇÃO)******************************************************************************************************************************

	public GraphicInterface() throws IOException {

		team1= new Team(0,"Benfica",80);
		team2= new Team(1,"Sporting",70);
		team3= new Team(2,"Porto",70);
		team4= new Team(3,"Braga",60);
		team5= new Team(4,"Estoril",50);
		team6= new Team(5,"Moreirense",40);
		teams = new ArrayList<Team>();
		teams.add(team1);teams.add(team2);teams.add(team3);teams.add(team4);
		teams.add(team5);teams.add(team6);

		mainPage = new JFrame("BetBall");mainPage.setResizable(false);
		results = new JFrame("BetBall");results.setResizable(false);
		moreInformation = new JFrame("BetBall");moreInformation.setResizable(false);
		classification = new JFrame("BetBall");classification.setResizable(false);
		bets = new JFrame("BetBall");bets.setResizable(false);

		mainPage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		results.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		moreInformation.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		classification.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		bets.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		addFrameContentInicio();
		addFrameContentResultados();
		addFrameContentInformacao();
		addFrameContentClassificacao();
		addFrameContentBets();

		mainPage.pack();
		results.pack();
		moreInformation.pack();
		classification.pack();
		bets.pack();
	}

	public void open() {
		results.setVisible(false);
		classification.setVisible(false);
		moreInformation.setVisible(false);
		mainPage.setVisible(true);
		mainPage.setSize(390, 520);
		mainPage.setLocationRelativeTo(null);
	}

	public void open2() {
		mainPage.setVisible(false);
		classification.setVisible(false);
		moreInformation.setVisible(false);
		results.setVisible(true);
		results.setSize(390, 520);
		results.setLocationRelativeTo(null);
	}

	public void open3() {
		mainPage.setVisible(false);
		results.setVisible(false);
		moreInformation.setVisible(false);
		classification.setVisible(true);
		classification.setSize(390, 520);
		classification.setLocationRelativeTo(null);
	}

	public void open4() {
		mainPage.setVisible(false);
		results.setVisible(false);
		classification.setVisible(false);
		moreInformation.setVisible(true);
		moreInformation.setSize(390, 520);
		moreInformation.setLocationRelativeTo(null);
	}



	//INICIO ***************************************************************************************************************************************************

	private void addFrameContentInicio() throws IOException {

		mainPage.setLayout(new BorderLayout());

		//CIMA *************************************************

		JPanel topPanel = new JPanel(new BorderLayout());
		mainPage.add(topPanel,BorderLayout.NORTH);

		balanceText= new JLabel();
		balanceText.setText(String.valueOf("Saldo: "+ balance));
		balanceText.setOpaque(true);balanceText.setBackground(Color.lightGray);
		topPanel.add(balanceText,BorderLayout.WEST);

		JLabel dayText= new JLabel();
		dayText.setHorizontalAlignment(JTextField.CENTER);
		dayText.setText(String.valueOf(day+" de Agosto"));
		dayText.setOpaque(true);dayText.setBackground(Color.lightGray);
		topPanel.add(dayText,BorderLayout.CENTER);

		JLabel errorText= new JLabel();errorText.setForeground(Color.red);

		JButton nextdaybutton = new JButton("Próximo dia");
		nextdaybutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(allGood()) {
					try {
						errorText.setText("");
						doneBet=false;
						betsText.setText("");
						listInformation.addElement("                                            ---- Dia "+ day+" ----");
						listInformation.addElement(" ");
						playTodayGames();
						addClassifications();
						if(doneBet)
							bets.setVisible(true);
					} catch (FileNotFoundException e1) {e1.printStackTrace();}

					day++;
					if(day==11) {
						dayText.setText(String.valueOf(day +" de Agosto"));
						nextdaybutton.setVisible(false);
						endText.setVisible(true);
					}else {
						dayText.setText(String.valueOf(day +" de Agosto"));
						try {
							addTodayGames();
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					errorText.setText("Valores das apostas invalidos...");
				}

			}

		});
		nextdaybutton.setBackground(Color.lightGray);
		topPanel.add(nextdaybutton,BorderLayout.EAST);

		//CENTRO ************************************************
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPage.add(centerPanel,BorderLayout.CENTER);
		
		String end= "FIM DO CAMPEONATO\nPara mais informação consultar RESULTADOS e CLASSIFICAÇÃO";
		endText = new JTextArea(end);
		endText.setPreferredSize(new Dimension(365, 410));
		endText.setVisible(false);
		centerPanel.add(endText);

		game1 = new JLabel();game1.setPreferredSize(new Dimension(210,110));game1.setFont(font);
		game2 = new JLabel();game2.setPreferredSize(new Dimension(210,110));game2.setFont(font);
		game3 = new JLabel();game3.setPreferredSize(new Dimension(210,130));game3.setFont(font);
		game1Money = new JTextField();game1Money.setPreferredSize(new Dimension(40,45));
		game2Money = new JTextField();game2Money.setPreferredSize(new Dimension(40,45));
		game3Money = new JTextField();game3Money.setPreferredSize(new Dimension(40,45));
		game1Bet = new JComboBox();game1Bet.setPreferredSize(new Dimension(100,25));
		game1Bet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String teamName = (String)cb.getSelectedItem();
				bet1=teamName;
			}
		});
		game2Bet = new JComboBox();game2Bet.setPreferredSize(new Dimension(100,25));
		game2Bet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String teamName = (String)cb.getSelectedItem();
				bet2=teamName;
			}
		});
		game3Bet = new JComboBox();game3Bet.setPreferredSize(new Dimension(100,25));
		game3Bet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String teamName = (String)cb.getSelectedItem();
				bet3=teamName;
			}
		});


		centerPanel.add(game1);centerPanel.add(game1Bet);centerPanel.add(game1Money);
		centerPanel.add(game2);centerPanel.add(game2Bet);centerPanel.add(game2Money);
		centerPanel.add(game3);centerPanel.add(game3Bet);centerPanel.add(game3Money);
		centerPanel.add(errorText);

		addTodayGames();


		//BAIXO *************************************************

		createTab(mainPage,1);

	}


	//RESULTADOS *********************************************************************************************************************************************

	private void addFrameContentResultados() {

		results.setLayout(new BorderLayout());

		//CIMA *************************************************

		JPanel topPanel = new JPanel(new BorderLayout());
		results.add(topPanel,BorderLayout.NORTH);

		//CENTRO *************************************************

		listGames = new DefaultListModel();
		JList games = new JList(listGames);games.setFont(font2);
		games.setLayoutOrientation(JList.VERTICAL);
		games.setOpaque(true);games.setBackground(new Color(230,230,230));
		JScrollPane listScroller = new JScrollPane(games);



		listScroller.setPreferredSize(new Dimension(18, 80));  
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		results.add(listScroller,BorderLayout.CENTER);

		//BAIXO *************************************************

		createTab(results,0);

	}

	//MAIS INFORMAÇÂO *****************************************************************************************************************************************

	private void addFrameContentInformacao() {

		moreInformation.setLayout(new BorderLayout());

		//CIMA *************************************************


		//CENTRO *************************************************

		listInformation = new DefaultListModel();
		JList games = new JList(listInformation);
		games.setLayoutOrientation(JList.VERTICAL);
		games.setOpaque(true);games.setBackground(new Color(230,230,230));
		JScrollPane listScroller = new JScrollPane(games);



		listScroller.setPreferredSize(new Dimension(18, 80));  
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		moreInformation.add(listScroller,BorderLayout.CENTER);

		//BAIXO *************************************************

		createTab(moreInformation,0);

	}

	//BETS *****************************************************************************************************************************************

	private void addFrameContentBets() {

		bets.setVisible(false);
		bets.setSize(320, 300);
		bets.setLocationRelativeTo(null);

		bets.setLayout(new BorderLayout());

		//CIMA *************************************************


		//CENTRO *************************************************

		betsText = new JTextArea(" ");
		betsText.setPreferredSize(new Dimension(320, 220));
		betsText.setFont(font);
		bets.add(betsText, BorderLayout.NORTH);

		//BAIXO *************************************************

		JButton backToApp = new JButton("Voltar");
		backToApp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				bets.setVisible(false);

			}
		});
		backToApp.setBackground(Color.lightGray);
		bets.add(backToApp,BorderLayout.SOUTH);


	}


	//ClASSIFICACAO ********************************************************************************************************************************************

	public String[] statusOfTeam(String team) {
		String[] status = new String[4];
		Team a= teamWithName(team);
		status[0]=team;
		status[1]=String.valueOf(a.getNumberGames());
		status[2]=String.valueOf(a.getGoalsScored()+":"+a.getGoalsSufered());
		status[3]=String.valueOf(a.getPoints());
		return status;
	}

	private void addFrameContentClassificacao() {

		//CIMA ***************************************************


		//CENTRO *************************************************

		addClassifications();
		String column[]={"EQUIPA","PJ","G","P"};  
		JTable table = new JTable(data,column);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(int x=1;x<4;x++){
			table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
		}

		JScrollPane sp=new JScrollPane(table);
		table.setRowHeight(45);
		classification.add(sp);
		//BAIXO *************************************************

		createTab(classification,1);


	}

	//FUNCOES AUXILIARES ********************************************************************************************************************************


	public void createTab(JFrame frame, int v) {
		JPanel bottomPanel = new JPanel();
		frame.add(bottomPanel,BorderLayout.SOUTH);
		bottomPanel.setBackground(red);

		JButton mainbutton = new JButton("Inicio");
		mainbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				open();

			}
		});
		mainbutton.setBackground(Color.lightGray);
		bottomPanel.add(mainbutton);

		JButton resultsbutton = new JButton("Resultados");
		resultsbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				open2();

			}
		});
		resultsbutton.setBackground(Color.lightGray);
		bottomPanel.add(resultsbutton);

		if(v==0) {

			JButton informationbutton = new JButton("Mais Informação");
			informationbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					open4();

				}
			});
			informationbutton.setBackground(Color.lightGray);
			bottomPanel.add(informationbutton);
		}

		if(v==1) {

			JButton classificationbutton = new JButton("Classificação");
			classificationbutton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					open3();

				}
			});
			classificationbutton.setBackground(Color.lightGray);
			bottomPanel.add(classificationbutton);
		}
	}


	public void addTodayGames() throws FileNotFoundException {
		game1.setText("");game2.setText("");game3.setText("");
		game1Bet.removeAllItems();game2Bet.removeAllItems();game3Bet.removeAllItems();
		InputStream stream = this.getClass().getResourceAsStream("/resources/Games");
		Scanner s = new Scanner(stream);
		int i=1;
		while(s.hasNextLine()) {
			String line= s.nextLine();
			String[] divided=line.split(",");
			if(Integer.parseInt(divided[2])==day) {
				Game g= new Game(teamWithName(divided[0]),teamWithName(divided[1]));
				double w=g.team1Winning();
				double od1= oddWithPercentage(w);
				double od2= oddWithPercentage(1-w+0.05);
				if(i==1) {
					setMainPageInfo(game1,game1Bet,divided[0],divided[1],od1,od2);
				}else if(i==2) {
					setMainPageInfo(game2,game2Bet,divided[0],divided[1],od1,od2);
				}else if(i==3) {
					setMainPageInfo(game3,game3Bet,divided[0],divided[1],od1,od2);
				}
				i++;
			}
		}
		bet1="Nenhuma";bet2="Nenhuma";bet3="Nenhuma";
		game1Money.setText("");game2Money.setText("");game3Money.setText("");
	}

	public void setMainPageInfo(JLabel game, JComboBox gameBet, String team1,String team2, double od1, double od2) {
		teamWithName(team1).setOdd(od1);teamWithName(team2).setOdd(od2);
		game.setText(team1+" ("+od1+")  vs  ("+od2+") "+team2);
		gameBet.addItem("Nenhuma");gameBet.addItem(team1);gameBet.addItem(team2);gameBet.addItem("Empate");
	}

	public double oddWithPercentage(double perc) {
		return new BigDecimal(1/perc).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void playTodayGames() throws FileNotFoundException {
		InputStream stream = this.getClass().getResourceAsStream("/resources/Games");
		Scanner s = new Scanner(stream);
		int i=1;
		while(s.hasNextLine()) {
			String line= s.nextLine();
			String[] divided=line.split(",");
			if(Integer.parseInt(divided[2])==day) {
				Game g= new Game(teamWithName(divided[0]),teamWithName(divided[1]));
				double finalTeam1 = g.team1Winning();
				Team winner= g.winner(finalTeam1);
				if(winner==null)doTheBets("null",i);
				else doTheBets(winner.getName(),i);
				writeGames(winner,divided[0],divided[1],g);
				i++;
			}
		}
	}
	
	public void doTheBets(String team, int game) {
		if(game==1) {
			runBets(game1Money,bet1,team);
		}else if(game==2) {
			runBets(game2Money,bet2,team);
		}else if(game==3) {
			runBets(game3Money,bet3,team);
		}
		balanceText.setText(String.valueOf("Saldo: "+ new BigDecimal(balance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
	}
	
	public void runBets(JTextField text,String bet, String team) {
		if(bet.equals(team)) { 
			executeBet(text, bet);doneBet=true;
		}else if(bet.equals("Nenhuma")) ;
		else if(bet.equals("Empate")) {
			if(team.equals("null")) { executeDraw(text);doneBet=true; }
			else {
				balance-=Integer.parseInt(text.getText());doneBet=true;
				betsText.append("Aposta no empate perdida, total perdido: "+Integer.parseInt(text.getText())+"\n"+"\n");
			}
		}
		else {
			balance-=Integer.parseInt(text.getText());doneBet=true;
			betsText.append("Aposta na equipa "+bet+ " perdida, total perdido: "+Integer.parseInt(text.getText())+"\n"+"\n");
		}
	}

	public void executeBet(JTextField text, String team) {
		int bet=Integer.parseInt(text.getText());balance-=bet;
		double l=new BigDecimal((bet*teamWithName(team).getOdd())).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		balance+=l;
		betsText.append("Aposta na equipa "+team+ " ganha, total ganho: "+l+"\n"+ "\n");

	}
	
	public void executeDraw(JTextField text) {
		int bet=Integer.parseInt(text.getText());balance-=bet;
		double l=new BigDecimal((bet*5)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		balance+=l;
		betsText.append("Aposta no empate ganha, total ganho: "+l+"\n");
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public Team teamWithName(String name) {
		for(Team a:teams) {
			if(a.getName().equals(name)) return a;
		}
		return null;
	}

	public void writeGames(Team winner, String team1,String team2, Game g) {
		if(winner==null) {
			while(true) {
				int n=g.numberOfGoals();
				if(n%2==0) {
					writeInfoFromGames(team1, team2, g, n/2, n/2,0);
					break;
				}
			}

		}else {
			int[] goals = getGoals(g);
			if(winner.getName().equals(team1)) {
				writeInfoFromGames(team1, team2, g, goals[0], goals[1],0);
			}else {
				writeInfoFromGames(team1, team2, g, goals[1], goals[0],1);
			}
		}
	}

	public void writeInfoFromGames(String team1, String team2, Game g, int rg1, int rg2, int v) {
		teamWithName(team1).setGoalsScored(rg1);teamWithName(team1).setGoalsSufered(rg2);
		teamWithName(team2).setGoalsScored(rg2);teamWithName(team2).setGoalsSufered(rg1);
		listInformation.addElement("JOGO:  "+team1+"  "+ rg1 +" - "+ rg2+ "  "+team2);
		listInformation.addElement("Tempos de golo: ( "+goalsTimes(rg1,g)+" | "+goalsTimes(rg2,g)+" )");
		double b=g.ballPossession();
		double b2=new BigDecimal(100-b).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		if(v==0) listInformation.addElement("Posse de bola: ( "+b+"%"+" | "+b2+"%"+" )");
		else listInformation.addElement("Posse de bola: ( "+b2+"%"+" | "+b+"%"+" )");
		listInformation.addElement(" ");
		listGames.addElement("08.21   "+team1+" vs "+team2+ "  |  "+ rg1+":"+rg2);
	}

	public String goalsTimes(int numberOfGoals, Game g) {
		ArrayList<Integer> str=g.timeOfGoals(numberOfGoals);
		String res="";
		for(Integer i:str) {
			if(str.indexOf(i)+1==str.size()) {
				res+=String.valueOf(i)+"´";
			}else {
				res+=String.valueOf(i)+"´ ";
			}
		}
		return res;
	}

	public int[] getGoals(Game g) {
		int[] goals= new int[2];
		while(true) {
			int n=g.numberOfGoals();
			if(n!=0) {
				while(true) {
					int winnerGoals=(int) (Math.random()*n) + 1;
					if(winnerGoals>n-winnerGoals) {
						goals[0]=winnerGoals;
						goals[1]=n-winnerGoals;
						break;
					}
				}
				break;
			}
		}
		return goals;
	}


	public void addClassifications() {
		ArrayList<Team> t=teams;
		Collections.sort(t, Comparator.comparing(s -> ((Team) s).getPoints()).reversed());
		int position=1;
		for(Team a: t) {
			String[] status=statusOfTeam(a.getName());
			data[position-1]=status;
			position++;
		}
	}

	public boolean allGood() {
		if(!bet1.equals("Nenhuma") && !isNumeric(game1Money.getText()))
			return false;
		if(!bet2.equals("Nenhuma") && !isNumeric(game2Money.getText()))
			return false;
		if(!bet3.equals("Nenhuma") && !isNumeric(game3Money.getText()))
			return false;

		if(!bet1.equals("Nenhuma") && !bet2.equals("Nenhuma") && !bet3.equals("Nenhuma")) {
			if(Integer.parseInt(game1Money.getText())+Integer.parseInt(game2Money.getText())+Integer.parseInt(game3Money.getText())>balance) return false;
			if(Integer.parseInt(game1Money.getText())<=0 || Integer.parseInt(game2Money.getText())<=0 || Integer.parseInt(game3Money.getText())<=0) return false;
		}else if(!bet1.equals("Nenhuma") && !bet2.equals("Nenhuma")) {
			if(Integer.parseInt(game1Money.getText())+Integer.parseInt(game2Money.getText())>balance) return false;
			if(Integer.parseInt(game1Money.getText())<=0 || Integer.parseInt(game2Money.getText())<=0) return false;
		}else if(!bet1.equals("Nenhuma") && !bet3.equals("Nenhuma")) {
			if(Integer.parseInt(game1Money.getText())+Integer.parseInt(game3Money.getText())>balance) return false;
			if(Integer.parseInt(game1Money.getText())<=0 || Integer.parseInt(game3Money.getText())<=0) return false;
		}else if(!bet2.equals("Nenhuma") && !bet3.equals("Nenhuma")) {
			if(Integer.parseInt(game2Money.getText())+Integer.parseInt(game3Money.getText())>balance) return false;
			if(Integer.parseInt(game2Money.getText())<=0 || Integer.parseInt(game3Money.getText())<=0) return false;
		}else if(!bet1.equals("Nenhuma")) {
			if(Integer.parseInt(game1Money.getText())>balance) return false;
			if(Integer.parseInt(game1Money.getText())<=0) return false;
		}else if(!bet2.equals("Nenhuma")) {
			if(Integer.parseInt(game2Money.getText())>balance) return false;
			if(Integer.parseInt(game2Money.getText())<=0) return false;
		}else if(!bet3.equals("Nenhuma")) {
			if(Integer.parseInt(game3Money.getText())>balance) return false;
			if(Integer.parseInt(game3Money.getText())<=0) return false;
		}

		return true;
	}












}

