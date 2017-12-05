package br.com.escolalura.escolalura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.escolalura.escolalura.models.Aluno;
import br.com.escolalura.escolalura.repositories.AlunoRepository;

@Controller
public class AlunoController {

	@Autowired
	private AlunoRepository repositorio;
	
	@GetMapping("/aluno/cadastrar")
	public String cadastrar(Model model){
		model.addAttribute("aluno",new Aluno());
		return "aluno/cadastrar";
	}
	
	@PostMapping("/aluno/salvar")
	public String salvar(@ModelAttribute Aluno aluno){
		repositorio.salvar(aluno);
		System.out.println(aluno);
		return "redirect:/";
	}
	
	@GetMapping("/aluno/listar")
	public String listar(Model model){
		List<Aluno> alunos = repositorio.obterTodosAlunos();
		model.addAttribute("alunos",alunos);
		return "aluno/listar";
	}
	
	
	
	
}
