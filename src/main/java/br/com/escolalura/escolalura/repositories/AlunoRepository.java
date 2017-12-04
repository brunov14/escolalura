package br.com.escolalura.escolalura.repositories;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.escolalura.escolalura.models.Aluno;

@Repository
public class AlunoRepository {
	
	
	public void salvar(Aluno aluno){
		MongoClient cliente = new MongoClient();
		MongoDatabase banco = cliente.getDatabase("test");
		MongoCollection<Aluno> alunos = banco.getCollection("alunos", Aluno.class);
		alunos.insertOne(aluno);
		cliente.close();
	}
}
