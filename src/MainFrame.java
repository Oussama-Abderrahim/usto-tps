import instruction.Instruction;
import theme.SButton;
import theme.SPanel;
import theme.Theme;
import token.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;
    private SPanel contentPane;
    private SPanel controlPanel;

    private SPanel outputPanel;

    private OutputTextArea EditorTextArea;
    private final OutputTextArea logTextArea;

    private String sourceCode = "";

    private LexicalEngine lexicalEngine;
    private SyntaxEngine syntaxEngine;
    private SemanticEngine semanticEngine;

    public MainFrame getInstance()
    {
        if(instance == null)
            instance = new MainFrame();
        return instance;
    }

    private MainFrame()
    {
        lexicalEngine = new LexicalEngine();
        syntaxEngine = new SyntaxEngine();
        semanticEngine = new SemanticEngine();

        initWindow();

        controlPanel = initControlPanel();
        EditorTextArea = new OutputTextArea(true);
        logTextArea = new OutputTextArea(false);
        outputPanel = initOutputPanel(EditorTextArea, logTextArea);

        contentPane.setLayout(new BorderLayout(5,5));
        contentPane.add(controlPanel, BorderLayout.WEST);
        contentPane.add(outputPanel, BorderLayout.CENTER);
    }

    private void initWindow()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mon analyseur lexical, syntaxique et sémantique");
        setBounds(100, 100, Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);

        contentPane = new SPanel();
        contentPane.setLayout(new BorderLayout(5,5));
        setContentPane(contentPane);
    }

    private SPanel initOutputPanel(OutputTextArea outputTextArea, OutputTextArea logTextArea)
    {
        SPanel outputPanel = new SPanel();
        outputPanel.setLayout(new BorderLayout(5,5));

        JLabel outLabel = new JLabel("Sortie : ");
        outLabel.setFont(Theme.FONT_DEFAULT);
        outLabel.setForeground(Theme.FONT_DEFAULT_COLOR);
        outputPanel.add(outLabel, BorderLayout.NORTH);

        JScrollPane scrollOutput = new JScrollPane(outputTextArea);
        JScrollPane scrollLog = new JScrollPane(logTextArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollOutput, scrollLog);
        splitPane.setDividerLocation(50);
        splitPane.setOneTouchExpandable(true);
        outputPanel.add(splitPane, BorderLayout.CENTER);
        return outputPanel;
    }

    private SPanel initControlPanel()
    {
        SPanel controlPanel = new SPanel();
        controlPanel.setLayout(new BorderLayout(5,5));

        JLabel cmdLabel = new JLabel("Commandes : ");
        cmdLabel.setFont(Theme.FONT_DEFAULT);
        cmdLabel.setForeground(Theme.FONT_DEFAULT_COLOR);

        controlPanel.add(cmdLabel, BorderLayout.NORTH);

        SPanel buttonsPanel = new SPanel();

        SButton loadFileButton = new SButton("Charger un fichier");
        SButton lexicalButton = new SButton("Analyse Lexicale");
        SButton syntaxButton = new SButton("Analyse Syntaxique");
        SButton SymanticButton = new SButton("Analyse Sémantique");

        loadFileButton.setIcon(FileManager.loadImage("magnifying_glass"));
        lexicalButton.setIcon(FileManager.loadImage("Letters-icon"));
        syntaxButton.setIcon(FileManager.loadImage("layers-icon"));
        SymanticButton.setIcon(FileManager.loadImage("source-code"));


        loadFileButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadSourceCode();
            }
        });
        lexicalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceCode = EditorTextArea.getText();
                lexicalEngine.setSourceCode(sourceCode);
                showLexicalResult(lexicalEngine.getTokenSource());
            }
        });
        syntaxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceCode = EditorTextArea.getText();
                lexicalEngine.setSourceCode(sourceCode);
                syntaxEngine.setTokenSource(lexicalEngine.getTokenSource());
                showSyntaxResult(syntaxEngine.getResult());
            }
        });
        SymanticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                semanticEngine.setInstructionSource(syntaxEngine.getResult());
                logTextArea.setText("Semantic Result : \n" + semanticEngine.getResult());
            }
        });

        buttonsPanel.setLayout(new GridLayout(4, 1, 10, 5));

        buttonsPanel.add(loadFileButton);
        buttonsPanel.add(lexicalButton);
        buttonsPanel.add(syntaxButton);
        buttonsPanel.add(SymanticButton);

        controlPanel.add(buttonsPanel, BorderLayout.CENTER);

        return controlPanel;
    }

    private void showSyntaxResult(ArrayList<Instruction> result)
    {
        logTextArea.setVisible(true);
        logTextArea.setText("");
        logTextArea.appendText("Syntax Analysis result : \n", Color.RED, false);

        // print instr :
        //logTextArea.appendText(s, Color.BLACK, false);


        for(Instruction s : result)
        {
            /*Print instr */
            String instr = "";
            for(Token t : s.getTokenList())
            {
                instr = instr + t.getText() + " ";
            }
            logTextArea.appendText(instr, Color.BLACK, true);

            /*Print type */
            logTextArea.appendText(" -> ", Color.BLACK, false);
            switch (s.getType())
            {
                case START_PROGRAM:
                    logTextArea.appendText("Debut Programme");
                    break;
                case END_PROGRAM:
                    logTextArea.appendText("Fin Programme");
                    break;
                case AFFECT_VALUE:
                    logTextArea.appendText("Affecation valeur à variable");
                    break;
                case AFFECT_VAR:
                    logTextArea.appendText("Affectation Variable à variable");
                    break;
                case SHOW_MSG:
                    logTextArea.appendText("Affichage message");
                    break;
                case IF_STATEMENT:
                    logTextArea.appendText("Condition");
                    break;
                case VAR_DECL:
                    logTextArea.appendText("Declaration variables");
                    break;
                case BEGIN:
                    logTextArea.appendText("Debut bloc instructions");
                    break;
                case FINISH:
                    logTextArea.appendText("Fin block instructions");
                    break;
                case ELSE_STATEMENT:
                    logTextArea.appendText("Else");
                    break;
                case SHOW_VAR:
                    logTextArea.appendText("Affichage Variable");
                    break;
                    default:
                        logTextArea.appendText("???");
            }

            logTextArea.appendText("\n");
        }
    }

    private void showLexicalResult(ArrayList<Token> tokenSource)
    {
        logTextArea.setVisible(true);
        logTextArea.setText("");
        for(Token t : tokenSource)
        {
            logTextArea.appendText(t.getText() + " -> ", Theme.FONT_INPUT_COLOR, true);
            switch (t.getType())
            {
                case DATA:
                    logTextArea.appendText("Donnée\n");
                    break;
                case ID:
                    logTextArea.appendText("Identificateur\n");
                    break;
                case TYPE:
                    logTextArea.appendText("Type variable\n");
                    break;
                case SYMBOL:
                    logTextArea.appendText("Symbol clé\n");
                    break;
                case KEYWORD:
                    logTextArea.appendText("Mot clé\n");
                    break;
                case ARITHMETIC:
                    logTextArea.appendText("Operateur arithmetic\n");
                    break;
                case LOGICAL:
                    logTextArea.appendText("Operateur logique\n");
                    break;
                case EOF:
                    break;
                default:
                    logTextArea.appendText("???\n");
            }
        }
    }


    private void loadSourceCode()
    {
        String sourceCode = FileManager.getLoadedFileText();

        EditorTextArea.setText(sourceCode);
        EditorTextArea.highlight();
        this.sourceCode = sourceCode;
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
