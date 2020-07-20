package br.ce.wcaquino.tasksfrontend.controller;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import junit.framework.Assert;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void descriptionIsEmpty () {
		
		Task task = new Task();
		
		task.setDueDate(LocalDate.now());
		
		try {
		
			controller.save(task);
		
			Assert.fail("Deveria ter ocorrido erro"); 
		
		}
		catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void dateIsEmpty() {
		Task task = new Task();
		
		task.setTask("Teste de tarefa");
		
		try {
		
			controller.save(task);
			
			Assert.fail("Deveria ter ocorrido erro");
		}
		catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void noPastDate() {
		Task task = new Task();
		
		task.setTask("Teste de tarefa");
		task.setDueDate(LocalDate.of(2010, 1, 1) );
		
		try {
		
			controller.save(task);
			
			Assert.fail("Deveria ter ocorrido erro");
		}
		catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void goodData () throws ValidationException {
		Task task = new Task();
		
		task.setDueDate(LocalDate.of(2030, 1, 1) );
		task.setTask("Tarefa Ok");
		
		controller.save(task);
		
		Mockito.verify(taskRepo).save(task);
		

	}
	
}
