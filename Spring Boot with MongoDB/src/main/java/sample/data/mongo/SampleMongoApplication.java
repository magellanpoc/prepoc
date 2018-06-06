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

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.diagnostics.logging.Loggers;

@SpringBootApplication
public class SampleMongoApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository repository;

	MongoOperations mongoOperation;
	List<Student> stu = new ArrayList<Student>();

	public void run(String... args) throws Exception {

		repository.deleteAll();

		// Initialization for database connection
		MongoClient mongo = new MongoClient("localhost", 27017);
		mongoOperation = new MongoTemplate(mongo, "Student");
		MongoTemplate mongoTemplate = new MongoTemplate(mongo, "Student");
		MongoDatabase db = mongo.getDatabase("Student");
		MongoCollection<Document> coll = db.getCollection("Student");
		// coll.drop();

		// Inserting records

		Student s1 = new Student("sudhi", "subu", 98, 21, "IT", "KEC");
		Student s2 = new Student("abc", "xyz", 99, 21, "IT", "KEC");
		Student s3 = new Student("xyz", "abc", 89, 20, "CSE", "MIT");
		Student s4 = new Student("alex", "smith", 100, 18, "EEE", "CIT");
		Student s5 = new Student("aaa", "bbb", 90, 20, "CSE", "MEC");
		Student s6 = new Student("manju", "Desi", 80, 19, "ECE", "CIT");
		Student s7 = new Student("alex", "pandian", 87, 23, "EEE", "MIT");
		stu.add(s4);
		stu.add(s3);
		stu.add(s2);
		stu.add(s1);
		stu.add(s7);
		stu.add(s6);
		stu.add(s5);
		mongoOperation.insert(stu, "Student");

		// Deleting records

		BasicQuery deletequery = new BasicQuery("{firstName:'xyz'}");
		mongoOperation.remove(deletequery, Student.class);
		Loggers.getLogger("");

		// updating

		Update update = new Update();
		Query updatequery = new Query(where("age").is(92));
		mongoTemplate.updateFirst(updatequery, update.set("age", 22), Student.class);
		//System.out.println("Update Query: " + updatequery.getQueryObject());

		// counting No of records

		BasicQuery countquery = new BasicQuery("{course:'IT'}");
		System.out.println("-------------------------------");
		System.out.println("\n No of Records with name sudhi:" + mongoOperation.count(countquery, Student.class));

		// finding records

		BasicQuery findquery = new BasicQuery("{ college:'KEC'}");
		for (Student student : mongoOperation.find(findquery, Student.class, "Student")) {
			System.out.println("\n Student in KEC: " + student.getFirstName());
		}

		// finding records by filters class

		FindIterable<Document> result = coll.find(Filters.lt("age", 20)).limit(3);
		for (Document doc : result) {
			System.out.println(" \n Name " + doc.getString("firstName") + " Mark " + doc.getInteger("age"));
		}

		// Can be used to find one record
		// mongoOperation.findOne(findquery,Student.class,"Student");

		// finding records by criteria

		System.out.println(" \n Find records by age > 20");
		System.out.println("-------------------------------");
		Query findquerycriteria = new Query();
		findquerycriteria.addCriteria(where("age").gt(20));
		for (Student studentlist : mongoTemplate.find(findquerycriteria, Student.class)) {
			System.out.println(studentlist.getFirstName() + " " + studentlist.getLastName());
		}

		// fetch Students like "a" and order by mark in des order through
		// repository method

		System.out.println(" \n Student like 'a' and order by mark in ASC order");
		System.out.println("--------------------------------");
		for (Student student : repository.findByFirstNameLikeOrderByMarkAsc("a")) {
			System.out.println(" \n Name:" + student.getFirstName() + "  Mark" + student.getMark());
		}

		// sorting the records

		System.out.println("\n Sorting records by age in asc");
		System.out.println("-------------------------------");
		Query sortquery = new Query();
		sortquery.with(new Sort(Sort.Direction.ASC, "age")).limit(4);
		for (Student student : mongoOperation.find(sortquery, Student.class, "Student")) {
			System.out.println("\n Student in ascending Age : " + student.getAge());
		}

		// Aggregating the no of students whose mark is less than 90 in
		// particular course

		mongoTemplate.dropCollection("aggregatedStudentcount");
		Aggregation agg = newAggregation(project("course", "mark"), match(where("mark").lt(90)),
				group("course").count().as("NoOfCourse"));
		AggregationResults<Student> res = mongoOperation.aggregate(agg, "Student", Student.class);

		// Check the result in mongo shell in the aggregatedStudentcount
		// collection

		mongoTemplate.insert(res.getRawResults(), "aggregatedStudentcount");

		// Aggregating by displaying the max(age) who are less in 20 in
		// particular course in list of colleges.

		mongoTemplate.dropCollection("aggregatedStudentminmark");
		Aggregation agg1 = newAggregation(project("age", "course", "college", "mark"), match(where("age").lt(20)),
				group("course", "college").max("age").as("max_age_lt_20"));
		AggregationResults<Student> res1 = mongoOperation.aggregate(agg1, "Student", Student.class);

		// Check the result in mongo shell in the aggregatedStudentminmark
		// collection

		System.out.println("Aggregate query: " + agg1);
		mongoTemplate.insert(res1.getRawResults(), "aggregatedStudentminmark");

		// using OR logical operators

		mongoTemplate.dropCollection("OrLogStudent");
		Query orquery = new Query();
		Criteria orcriteria = new Criteria();
		orcriteria.orOperator(where("college").is("KEC"), where("college").is("MIT"));
		orquery.addCriteria(orcriteria);
		// Check the result in mongo shell in the OrLogStudent
		// collection
		mongoTemplate.insert(mongoTemplate.find(orquery, Student.class), "OrLogStudent");

		// using AND logical operators

		mongoTemplate.dropCollection("AndLogStudent");
		Query andquery = new Query();
		Criteria andcriteria = new Criteria();
		andcriteria.orOperator(where("college").is("KEC"), where("course").is("IT"));
		andquery.addCriteria(andcriteria);
		// Check the result in mongo shell in the AndLogStudent
		// collection
		mongoTemplate.insert(mongoTemplate.find(andquery, Student.class), "AndLogStudent");

		// AND Operation through filters

		Bson andfilter = Filters.and(Filters.eq("college", "KEC"), Filters.eq("course", "IT"));
		FindIterable<Document> andresult = coll.find(andfilter);
		// Check the result in mongo shell in the AndLogStudentFilter
		// collection
		for (Document doc : andresult) {
			mongoTemplate.insert(doc, "AndLogStudentFilter");
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleMongoApplication.class, args);
	}

}
