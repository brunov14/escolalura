package br.com.escolalura.escolalura.codecs;

import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import br.com.escolalura.escolalura.models.Aluno;
import br.com.escolalura.escolalura.models.Curso;

public class AlunoCodec implements CollectibleCodec<Aluno>{

	private Codec<Document> codec;
	
	public AlunoCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Aluno aluno, EncoderContext encoder) {
		
		ObjectId id = aluno.getId();
		String nome = aluno.getNome();
		Date dataNascimento = aluno.getDataNascimento();
		Curso curso = aluno.getCurso();
		
		Document document = new Document();
		document.put("_id", id);
		document.put("nome", nome);
		document.put("data_nascimento", dataNascimento);
		document.put("curso", new Document("nome", curso.getNome()));
		
		codec.encode(writer, document, encoder);
		
		
		
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoder) {
		Document documento = this.codec.decode(reader, decoder);
		
		Aluno aluno = new Aluno();
		aluno.setId(documento.getObjectId("_id"));
		aluno.setNome(documento.getString("nome"));
		aluno.setDataNascimento(documento.getDate("data_nascimento"));
		
		Document curso = (Document) documento.get("curso");
		if(curso != null){
			String nomeCurso = curso.getString("nome");
			aluno.setCurso(new Curso(nomeCurso));
		}
		
		
		return aluno;
	}

	@Override
	public boolean documentHasId(Aluno aluno) {
		return aluno.getId() == null;
	}

	@Override
	public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
		return documentHasId(aluno) ? aluno.criaId() : aluno;
	}

	@Override
	public BsonValue getDocumentId(Aluno aluno) {
		
		if(!documentHasId(aluno)){
			throw new IllegalStateException("Esse documento não tem Id.");
		}
		
		return new BsonString(aluno.getId().toHexString());
	}

}
