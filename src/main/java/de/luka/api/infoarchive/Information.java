package de.luka.api.infoarchive;

import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import de.luka.api.infotags.Tag;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Information")
public class Information {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long information_id;
	
	private String name; 
	private String description;
	private String htmlContent;
	private URL url;
	private boolean validated;
	private Instant lastVisited;
	

	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "information_tag", 
            joinColumns = { @JoinColumn(name = "information_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
       )
	private Set<Tag> tags = new HashSet<>();
	
	public boolean isValidated() {
		return validated;
	}
	public Instant getLastVisited() {
		return lastVisited;
	}
	public void setLastVisited(Instant lastVisited) {
		this.lastVisited = lastVisited;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public URL getUrl() {
		return url;
	}
	public long getid() {
		return information_id;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	
}
