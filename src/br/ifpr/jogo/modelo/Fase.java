package br.ifpr.jogo.modelo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener, KeyListener {
    private Image imagemfundo;
    private Personagem personagem;
    private static final int DELAY = 5;
    private Timer timer;
    private Object Tiro;
    private br.ifpr.jogo.modelo.Tiro tiro;
    private Object Inimigo;
    private br.ifpr.jogo.modelo.Inimigo[] inimigo;
    private Object inimigos;
    private static final int LARGURA_DA_JANELA = 1900;
    private static final int Quantidade_de_inimigos = 0;

    public Fase() {
        setFocusable(true);
        setDoubleBuffered(true);
        ImageIcon carregando = new ImageIcon("recursos\\imagemfundo.png");
        imagemfundo = carregando.getImage();
        personagem = new Personagem();
        personagem.carregar();

        timer.start();

        this.inicializaInimigos();
        inimigos = new ArrayList<Inimigo>();
        for (int i = 0; i < Quantidade_de_inimigos; i++) {
            int x = (int) (Math.random() * 8000 + 1024);
            int y = (int) (Math.random() * 650 + 30);
            Inimigo inimigo = new Inimigo(x, y);
            inimigos.add(inimigo);
        }

        addKeyListener(this);
        timer = new Timer(DELAY, this);
        timer.start();

    }

    private void inicializaInimigos() {
    }

    public void paint(Graphics g) {
        Graphics2D graficos = (Graphics2D) g;
        graficos.drawImage(imagemfundo, 0, 0, null);
        graficos.drawImage(personagem.getImagem(), personagem.getPosicaoEmX(), personagem.getPosicaoEmY(), this);

        ArrayList<Tiro> tiros = personagem.getTiros();

        for (Tiro tiro : tiros) {
            tiro.carregar();
            graficos.drawImage(tiro.getImagem(), tiro.getPosicaoEmX(), tiro.getPosicaoEmY(), this);

        }

        for (Inimigo inimigo : inimigos()) {
            inimigo.carregar();
            graficos.drawImage(inimigo.getImagem(), inimigo.getPosicaoEmX(), inimigo.getPosicaoEmY(), this);
        }

        g.dispose();
    }

    private br.ifpr.jogo.modelo.Inimigo inimigos() {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        personagem.atualizar();

        ArrayList<Tiro> tiros = personagem.getTiros();

        for (int i = 0; i < tiros.size(); i++) {
            Tiro tiro = tiros.get(i);
            if (tiro.getPosicaoEmX() > LARGURA_DA_JANELA)
                tiros.remove(tiro);

            else
                tiro.atualizar();

        }
        for (int i = 0; i < this.inimigos.size(); i++) {
            Inimigo inimigo = this.inimigos.get(i);

            if (inimigo.getPosicaoEmX() > 0)
                inimigos.remove(inimigo);

            else
                inimigo.atualizar();

        }

    }

    repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            personagem.atirar();
        } else
            personagem.mover(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        personagem.parar(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
}
