package br.com.escolalura.escolalura.models;

public class Curso {
	
	private String nome;

	public Curso() {
		super();
	}

	public Curso(String nomeCurso) {
		this.nome = nomeCurso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
