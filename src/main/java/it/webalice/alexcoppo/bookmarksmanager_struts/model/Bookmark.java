package it.webalice.alexcoppo.bookmarksmanager_struts.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="BOOKMARKS")
@NamedQueries({
	@NamedQuery(
		name="Bookmark.allUntaggedAsc",
		query="SELECT b FROM Bookmark b WHERE b.tags.size = 0 ORDER BY b.URL ASC"
		),
	@NamedQuery(
		name="Bookmark.allByTagAsc",
		query="SELECT bt.bookmark FROM BookmarkTag bt WHERE bt.tag.tag = :tag ORDER BY bt.bookmark.URL ASC"
		),
	@NamedQuery(
		name="Bookmark.allByUrlLikeAsc",
		query="SELECT b FROM Bookmark b WHERE b.URL LIKE :urlPattern ORDER BY b.URL ASC"
		),
	@NamedQuery(
		name="Bookmark.findByUrl",
		query="SELECT b FROM Bookmark b WHERE b.URL = :url"
		)
})
public class Bookmark {
	private long id;
	private String url;
	private Set<Tag> tags;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="URL", length=255, unique=true)
	public String getURL() {
		return url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
   		name="BOOKMARK_TAGS",
    	joinColumns = { @JoinColumn(name="BOOKMARK_ID") },
    	inverseJoinColumns = { @JoinColumn(name="TAG_ID") })	
	public Set<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	@Transient
	public String getDomain() {
		try {
			URL url = new URL(getURL());

			return url.getHost();
		} catch (MalformedURLException e) {
			return "";
		}
	}
}
