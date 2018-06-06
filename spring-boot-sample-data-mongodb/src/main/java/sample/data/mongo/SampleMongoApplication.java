/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleMongoApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();

		// save a couple of Students
		repository.save(new Student("Alice", "Smith"));
		repository.save(new Student("Bob", "Smith"));
		repository.save(new Student("sudhi", "subu"));
		
	
		// fetch all Students
		System.out.println("Students found with findAll():");
		System.out.println("-------------------------------");
		for (Student Student : repository.findAll()) {
			System.out.println(Student);
		}
		System.out.println();
		
		
		// fetch an individual Student
		
		System.out.println("Student found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));
		
		//deleting
		repository.deleteByFirstName("Alice");
		
		// fetch all Students
				System.out.println("Students found with findAll():");
				System.out.println("-------------------------------");
				for (Student Student : repository.findAll()) {
					System.out.println(Student);
				}
				System.out.println();
		
		
		//updating
		
		Student student = repository.findByFirstName("Bob");
		student.setFirstName("abi");
		repository.save(student);
		
		// fetch all Students
		
		System.out.println("Students found with findAll():");
		System.out.println("-------------------------------");
		for (Student Student : repository.findAll()) {
			System.out.println(Student);
		}
		
		//counting number of records
		System.out.println(" /n Total no of students:"+repository.count());
		
		
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleMongoApplication.class, args);
	}

}
