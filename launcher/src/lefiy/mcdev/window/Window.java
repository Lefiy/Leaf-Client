package lefiy.mcdev.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import lefiy.mcdev.Main;
import lefiy.mcdev.security.SecureReader;
import lefiy.mcdev.settings.Reader;
import lefiy.mcdev.window.components.Button;
import lefiy.mcdev.window.components.ButtonGroup;
import lefiy.mcdev.window.components.CountText;
import lefiy.mcdev.window.components.LaunchButton;
import lefiy.mcdev.window.components.NewsPanel;
import lefiy.mcdev.window.components.ScrollBar;
import lefiy.mcdev.window.components.SelectBar;
import lefiy.mcdev.window.components.SelectButton;
import lefiy.mcdev.window.components.SystemButton;
import lefiy.mcdev.window.components.Text;
import lefiy.mcdev.window.components.TextBox;
import lefiy.mcdev.window.components.ToggleBox;
import lefiy.mcdev.window.utils.Animation;
import lefiy.mcdev.window.utils.DownloadData;

public class Window extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private final Reader SETTING;
	private final SecureReader INFO;
	private final DownloadData DATA;
	
	private int selected;
	
	private static JFrame frame;
	
	private Screen animation = null, language = null, pass = null, launcher = null, version = null, setting = null;
	
	private Point pressedPoint;
    private Rectangle frameBounds;

	public Window(Reader reader, SecureReader info, DownloadData data) {
		this.SETTING = reader;
		this.SETTING.reload();
		this.INFO = info;
		this.DATA = data;
		this.setup();
	}

	private void setup() {
		
		frame = this;
		frame.setSize(1200, 650);
		
		frame.setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("LeafClient Launcher");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
	    
	    this.selected = 0;
	    
	    SelectBar l_selectbar; CountText counttext;

		List<Integer> w_news = new ArrayList<>(); int index = 0;
		
		switch(DATA.getNews().size()) {
			case 1:
				w_news = Arrays.asList(frame.getWidth() / 2 - 150); break;
			case 2:
				w_news = Arrays.asList(frame.getWidth() + 200, frame.getWidth() - 200); break;
			case 3:
				w_news = Arrays.asList(frame.getWidth() + 100, frame.getWidth() / 2 - 150, frame.getWidth() - 100); break;
		}
		
		List<Component> components = new ArrayList<>();
		
		for(int width : w_news) {
			components.add(new NewsPanel(DATA.getNews().get(index), width, frame.getHeight() - 250, 300, 200, this));
			index++;
		}
		
		components.addAll(Arrays.asList(
				l_selectbar = new SelectBar(Arrays.asList(11, 12, 13), getWidth() / 2 - 250, 50, 500, 50, 11) {
					private static final long serialVersionUID = 1L;
	    			@Override
	    			public void onClick() {
	    				int num = getSelected();
	    				if(num == 12) {
	    					frame.setContentPane(version);
	    					version.doThings();
	    				} else if(num == 13) {
	    					frame.setContentPane(setting);
	    					setting.doThings();
	    				}
	    			}
	    		},
				counttext = new CountText(DATA.getDownloads(), getWidth() / 2 + 265, 68, 215, 40, 38),
				new LaunchButton(9, 10, getWidth() / 2 - 150, getHeight() / 2 - 80, 300, 80, this) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						Main.getMain().launch();
					}
					@Override
					public void onSubClick() {
						frame.setContentPane(version);
    					version.doThings();
					}
				},
				new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						System.exit(0);
					}
				}
	    ));

	    launcher = new Screen("background.png", frame.getWidth(), frame.getHeight(), components) {
			private static final long serialVersionUID = 1L;
			@Override
			public void doThings() {
				if(selected == 0) {
					this.animation = new Animation();
					this.animation.startUpFloat(0.1F, 1);
					selected = 12;
				}
				l_selectbar.doThings(selected, 11); selected = 11;
				counttext.doThings();
			}
		};
		
		launcher.setLayout(null);
		launcher.setBounds(0, 0, 1280, 720);
		launcher.setBackground(new Color(40, 38, 40));
	    
	    SelectBar s_selectbar;
	    
	    setting = new Screen(null, frame.getWidth(), frame.getHeight(), Arrays.asList(
	    		s_selectbar = new SelectBar(Arrays.asList(11, 12, 13), getWidth() / 2 - 250, 50, 500, 50, 13) {
					private static final long serialVersionUID = 1L;
	    			@Override
	    			public void onClick() {
	    				int num = getSelected();
	    				if(num == 11) {
	    					frame.setContentPane(launcher);
	    					launcher.doThings();
	    				} else if(num == 12) {
	    					frame.setContentPane(version);
	    					version.doThings();
	    				}
	    			}
	    		},
	    		new Text(18, getWidth() / 2, 150, 400, 30, 20),
	    		new ScrollBar(getWidth() / 2 - 150, 220, 300, 80, Main.getMain().ram) {
					private static final long serialVersionUID = 1L;
					@Override
	    			public void doThings(int value) {
						int ram = 1;
	    				if(value > 10) {
	    					ram = (value / 10) + 1;
	    				}
	    				Main.getMain().ram = String.valueOf(ram);
	    				SETTING.writer("ram", String.valueOf(ram));
	    			}
	    		},
	    		new Text(1, getWidth() / 2, getHeight() / 2 + 15, 400, 30, 20),
	    		new ButtonGroup(
	    		new SelectButton("jp.png", getWidth() / 2 + 50, getHeight() - 210, 200, 150, 40, this, Main.getMain().lang.equals("jp")) {
	    			private static final long serialVersionUID = 1L;
	    			@Override
	    			public void onClick() {
	    				Main.getMain().lang = "jp";
	    				SETTING.reload();
	    				SETTING.writer("language", "jp");
	    				frame.repaint();
	    			}
	    		},
	    		new SelectButton("en.png", getWidth() / 2 - 250, getHeight() - 210, 200, 150, 40, this, Main.getMain().lang.equals("en")) {
	    			private static final long serialVersionUID = 1L;
	    			@Override
	    			public void onClick() {
	    			Main.getMain().lang = "en";
	    				SETTING.reload();
	    				SETTING.writer("language", "en");
	    				frame.repaint();
	    			}
	    		}),
	    		new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						System.exit(0);
					}
				}
	    	)){
			private static final long serialVersionUID = 1L;
			@Override
			public void doThings() {
				s_selectbar.doThings(selected, 13); selected = 13;
			}
		};
		setting.setLayout(null);
		setting.setBackground(new Color(40, 38, 40));
		setting.setBounds(0, 0, 1280, 720);
	    
	    SelectBar v_selectbar; ToggleBox box_1_8, box_1_7;
	    
	    version = new Screen(null, frame.getWidth(), frame.getHeight(), Arrays.asList(
	    		v_selectbar = new SelectBar(Arrays.asList(11, 12, 13), getWidth() / 2 - 250, 50, 500, 50, 12) {
					private static final long serialVersionUID = 1L;
	    			@Override
	    			public void onClick() {
	    				int num = getSelected();
	    				if(num == 11) {
	    					frame.setContentPane(launcher);
	    					launcher.doThings();
	    				} else if(num == 13) {
	    					frame.setContentPane(setting);
	    					setting.doThings();
	    				}
	    			}
	    		},
				box_1_8 = new ToggleBox("1.8", getWidth() / 2 + 240, getHeight() / 2 - 50, 200, 300, this) {
					private static final long serialVersionUID = 1L;
					@Override
		    		public void onClick(String version, String name, boolean toggle) {
						String value = Main.getMain().f_1_8;
						if(toggle) {
							if(!value.contains(name)) {
								value += (value.equals("") ? "" : ",") + name;
							}
						} else {
							if(value.contains(name)) {
								if(value.contains(",")) {
									if(value.contains(name + ",")) {
										value = value.replace(name + ",", "");
									} else {
										value = value.replace("," + name, "");
									}
								} else { value = ""; }
							}
						}
						Main.getMain().f_1_8 = value;
						SETTING.writer("1.8-forge-mod", value);
		    		}
		    	},
				box_1_7 = new ToggleBox("1.7", getWidth() / 2 - 100, getHeight() / 2 - 50, 200, 300, this) {
					private static final long serialVersionUID = 1L;
					@Override
		    		public void onClick(String version, String name, boolean toggle) {
						String value = Main.getMain().f_1_7;
						if(toggle) {
							if(!value.contains(name)) {
								value += (value.equals("") ? "" : ",") + name;
							}
						} else {
							if(value.contains(name)) {
								if(value.contains(",")) {
									if(value.contains(name + ",")) {
										value = value.replace(name + ",", "");
									} else {
										value = value.replace("," + name, "");
									}
								} else { value = ""; }
							}
						}
						Main.getMain().f_1_7 = value;
						SETTING.writer("1.7-forge-mod", value);
		    		}
		    	},
				new Text(14, getWidth() / 2, 150, 400, 30, 20),
				new Text(15, getWidth() / 2, 220, 400, 30, 15),
				new Text(16, getWidth() / 2, 260, 400, 30, 15),
				new Text(17, getWidth() / 2, 300, 400, 30, 15),
			new ButtonGroup(
					new SelectButton("optifine.jpg", getWidth() / 2 - 235, getHeight() / 2 + 150, 60, 60, 25, this, Main.getMain().m_1_7.equals("optifine")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							Main.getMain().m_1_7 = "optifine";
							SETTING.writer("1.7-module", "optifine");
							frame.repaint();
						}
					},
					new SelectButton("forge.jpg", getWidth() / 2 - 165, getHeight() / 2 + 150, 60, 60, 25, this, Main.getMain().m_1_7.equals("forge")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							box_1_7.setCompVisible(true);
							Main.getMain().m_1_7 = "forge";
							SETTING.writer("1.7-module", "forge");
							frame.repaint();
						}
					}
				),
			new ButtonGroup(
					new SelectButton("optifine.jpg", getWidth() / 2 + 105, getHeight() / 2 + 150, 60, 60, 25, this, Main.getMain().m_1_8.equals("optifine")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							Main.getMain().m_1_8 = "optifine";
							SETTING.writer("1.8-module", "optifine");
							frame.repaint();
						}
					},
					new SelectButton("forge.jpg", getWidth() / 2 + 175, getHeight() / 2 + 150, 60, 60, 25, this, Main.getMain().m_1_8.equals("forge")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							box_1_8.setCompVisible(true);
							Main.getMain().m_1_8 = "forge";
							SETTING.writer("1.8-module", "forge");
							frame.repaint();
						}
					}
				),
			new ButtonGroup(
				new SelectButton("1.7.png", getWidth() / 2 - 320, getHeight() / 2 + 60, 300, 180, 40, this, Main.getMain().ver.equals("1.7")) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						Main.getMain().ver = "1.7";
						SETTING.writer("version", "1.7");
						frame.repaint();
					}
				},
				new SelectButton("1.8.png", getWidth() / 2 + 20, getHeight() / 2 + 60, 300, 180, 40, this, Main.getMain().ver.equals("1.8")) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						Main.getMain().ver = "1.8";
						SETTING.writer("version", "1.8");
						frame.repaint();
					}
				}),
			new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						System.exit(0);
					}
			})){
			private static final long serialVersionUID = 1L;
			@Override
			public void doThings() {
				v_selectbar.doThings(selected, 12); selected = 12;
			}
		};
		version.setLayout(null);
		version.setBackground(new Color(40, 38, 40));
		version.setBounds(0, 0, 1280, 720);
	    
	    if(!Main.getMain().setup) {
	    
		    TextBox passward;
			
		    pass = new Screen(null, frame.getWidth(), frame.getHeight(), Arrays.asList(
		    		new Text(5, getWidth() / 2, 50, 400, 30, 20),
					new Text(6, getWidth() / 2, 110, 400, 30, 15),
					new Text(7, getWidth() / 2, 140, 400, 30, 15),
					new Text(8, getWidth() / 2, 170, 400, 30, 15),
					passward = new TextBox(getWidth() / 2 - 250, getHeight() / 2 - 20, 500, 40),
		    		new Button(0, getWidth() / 2 - 50, getHeight() - 100, 100, 40) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							String passw = new String(passward.getPassword());
							if(passw.equals("") || passw.contains(" ")) {
								passward.setText(""); return;
							}
							INFO.setKey(new String(passw));
							SETTING.writer("setup", true);
							pass.endScreen();
						}
					},
		    		new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							System.exit(0);
						}
					}
		    		)) {
				private static final long serialVersionUID = 1L;
				@Override
				public void doThings() {
					this.animation.startUpFloat(0.1F, 1);
				}
				@Override
				public void endScreen() {
					this.animation = new Animation() {
						@Override
						public void endAnimation() {
							frame.setContentPane(launcher);
							launcher.doThings();
						}
					};
					this.animation.startDownFloat(1.0F, 1);
					pass.repaint();
				}
			};
			
			pass.setLayout(null);
			pass.setBounds(0, 0, 1280, 720);
			pass.setBackground(new Color(40, 38, 40));
			
			language = new Screen(null, frame.getWidth(), frame.getHeight(), Arrays.asList(
					new Text(1, getWidth() / 2, 50, 400, 30, 20),
					new Text(2, getWidth() / 2, 110, 400, 30, 15),
					new Text(3, getWidth() / 2, 140, 400, 30, 15),
					new Text(4, getWidth() / 2, 170, 400, 30, 15),
					new Button(0, getWidth() / 2 - 50, getHeight() - 100, 100, 40) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							language.endScreen();
						}
					},
					new ButtonGroup(
					new SelectButton("jp.png", getWidth() / 2 + 50, getHeight() / 2 - 75, 200, 150, 40, this, Main.getMain().lang.equals("jp")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							Main.getMain().lang = "jp";
							SETTING.reload();
							SETTING.writer("language", "jp");
							frame.repaint();
						}
					},
					new SelectButton("en.png", getWidth() / 2 - 250, getHeight() / 2 - 75, 200, 150, 40, this, Main.getMain().lang.equals("en")) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							Main.getMain().lang = "en";
							SETTING.reload();
							SETTING.writer("language", "en");
							frame.repaint();
						}
					}),
					new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							System.exit(0);
						}
				})){
				private static final long serialVersionUID = 1L;
				@Override
				public void doThings() {
					this.animation.startUpFloat(0.0F, 1);
				}
				@Override
				public void endScreen() {
					this.animation = new Animation() {
						@Override
						public void endAnimation() {
							frame.setContentPane(pass);
							pass.doThings();
						}
					};
					this.animation.startDownFloat(1.0F, 1);
					language.repaint();
				}
			};
			language.setLayout(null);
			language.setBackground(new Color(40, 38, 40));
			language.setBounds(0, 0, 1280, 720);
		
	    } else if(INFO.getKey() == null) {
	    	
	    	TextBox passward;
	    	
	    	pass = new Screen(null, frame.getWidth(), frame.getHeight(), Arrays.asList(
		    		new Text(5, getWidth() / 2, 50, 400, 30, 20),
					new Text(6, getWidth() / 2, 110, 400, 30, 15),
					new Text(7, getWidth() / 2, 140, 400, 30, 15),
					new Text(8, getWidth() / 2, 170, 400, 30, 15),
					passward = new TextBox(getWidth() / 2 - 250, getHeight() / 2 - 20, 500, 40),
		    		new Button(0, getWidth() / 2 - 50, getHeight() - 100, 100, 40) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							String passw = new String(passward.getPassword());
							if(passw.equals("") || passw.contains(" ")) {
								passward.setText(""); return;
							}
							INFO.setKey(new String(passw));
							pass.endScreen();
						}
					},
		    		new SystemButton("exit.png", getWidth() - 50, 10, 40, 40, this) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick() {
							System.exit(0);
						}
					}
		    		)) {
				private static final long serialVersionUID = 1L;
				@Override
				public void doThings() {
					this.animation.startUpFloat(0.1F, 1);
				}
				@Override
				public void endScreen() {
					this.animation = new Animation() {
						@Override
						public void endAnimation() {
							frame.setContentPane(launcher);
							launcher.doThings();
						}
					};
					this.animation.startDownFloat(1.0F, 1);
					pass.repaint();
				}
			};
			
			pass.setLayout(null);
			pass.setBounds(0, 0, 1280, 720);
			pass.setBackground(new Color(40, 38, 40));
	    	
	    }
		
		animation = new Screen("load.gif", frame.getWidth(), frame.getHeight(), null) {
			private static final long serialVersionUID = 1L;
			@Override
			public void doThings() {
				this.animation = new Animation() {
					@Override
					public void endAnimation() {
						if(!Main.getMain().setup) {
							frame.setContentPane(language);
							language.doThings();
						} else if(INFO.getKey() == null) {
							frame.setContentPane(pass);
							pass.doThings();
						} else {
							frame.setContentPane(launcher);
							launcher.doThings();
						}
					}
				};
				this.animation.wait(100);
			}
		};
		animation.setLayout(null);
		animation.setBackground(new Color(40, 38, 40));
		animation.setBounds(0, 0, 1280, 720);
		
		frame.setContentPane(animation);
		
		frame.setVisible(true);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		animation.doThings();
	}
	
	public static void open() {
		frame.setVisible(true);
	}
	
	public static void close() {
		frame.setVisible(false);
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {}
	@Override
	public void mouseClicked(MouseEvent event) {}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		
		Point endPoint = event.getPoint();
        
        int xDiff = endPoint.x - pressedPoint.x;
        int yDiff = endPoint.y - pressedPoint.y;
        frameBounds.x += xDiff;
        frameBounds.y += yDiff;
        frame.setBounds(frameBounds);
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {}
	@Override
	public void mouseExited(MouseEvent event) {}
	
	@Override
	public void mousePressed(MouseEvent event) {
		this.frameBounds = frame.getBounds();
        this.pressedPoint = event.getPoint();
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		
		Point endPoint = event.getPoint();
        
        int xDiff = endPoint.x - pressedPoint.x;
        int yDiff = endPoint.y - pressedPoint.y;
        frameBounds.x += xDiff;
        frameBounds.y += yDiff;
        frame.setBounds(frameBounds);
	}
}