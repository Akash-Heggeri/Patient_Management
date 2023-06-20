package com.example.springboot_assignment.entity;


import com.example.springboot_assignment.model.ClinicalModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.Min;


@Entity
@Table(name = "Clinical")
public class ClinicalEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "height", nullable = false)
	@Min(value=0, message="must be a positive number")
	private int height;
	
	@Column(name = "weight", nullable = false)
	@Min(value=0, message="must be a positive number")
	private int weight;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "Clinical_allergies_id", referencedColumnName = "id")
	private List<AllergiesEntity> allergies;

	public ClinicalEntity(Long id, int height, int weight, ArrayList<AllergiesEntity> allergies) {
		super();
		//this.id = id;
		this.allergies = allergies;
		this.height = height;
		this.weight = weight;
	}

	public ClinicalEntity(int height, int weight, ArrayList<AllergiesEntity> allergies) {
		super();
		//this.id = id;
		this.allergies = allergies;
		this.height = height;
		this.weight = weight;
	}

	public ClinicalEntity(ClinicalModel clinicalModel){
		this.id = clinicalModel.getId();
		this.height = clinicalModel.getHeight();
		this.weight = clinicalModel.getWeight();

		this.allergies = clinicalModel.getAllergies().stream()
				.map(AllergiesEntity::new).collect(Collectors.toList());

	}

	public ClinicalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	public List<AllergiesEntity> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<AllergiesEntity> allergies) {
		this.allergies = allergies;
	}


}
