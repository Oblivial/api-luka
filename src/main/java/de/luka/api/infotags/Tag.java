package de.luka.api.infotags;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.luka.api.infoarchive.Information;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@Table(name = "Tag")
public class Tag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long tag_id;
	private String name;
	
	
	@ManyToMany(mappedBy = "tags")
    
	//@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    //@JoinColumn(name="information_id", nullable=false)
	//private Information information;
    private Set<Information> relatedInformation = new HashSet<>();
	
	/*
	DANGEROUS: public Set<Information> getRelatedInformation() {
		return relatedInformation;
	}*/
	
	public void setRelatedInformation(Set<Information> relatedInformation) {
		this.relatedInformation = relatedInformation;
	}
	public void addRelatedInformation(Information i) {
		this.relatedInformation.add(i);
	}
    
	public Long getTag_id() {
		return tag_id;
	}
	public void setTag_id(Long tag_id) {
		this.tag_id = tag_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}
