package it.webalice.alexcoppo.bookmarksmanager_struts.model;

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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="TAGS")
@NamedQueries({
	@NamedQuery(
		name="Tag.allByTagAsc",
		query="SELECT t FROM Tag t ORDER BY t.tag ASC"
		),
	@NamedQuery(
		name="Tag.allByTagDesc",
		query="SELECT t FROM Tag t ORDER BY t.tag DESC"
		),
	@NamedQuery(
		name="Tag.allByIdAsc",
		query="SELECT t FROM Tag t ORDER BY t.id ASC"
		),
	@NamedQuery(
		name="Tag.allByIdDesc",
		query="SELECT t FROM Tag t ORDER BY t.id DESC"
		),
	@NamedQuery(
		name="Tag.findByTag",
		query="SELECT t FROM Tag t WHERE t.tag = :tag"
		),
	@NamedQuery(
		name="Tag.listUsedByBookmark",
		query="SELECT bt.tag FROM BookmarkTag bt WHERE bt.bookmark.id = :bookmarkId ORDER BY bt.tag.tag ASC"
		),
	@NamedQuery(
		name="Tag.listUnusedByBookmark",
		query="SELECT t FROM Tag t WHERE t NOT IN (SELECT bt.tag FROM BookmarkTag bt WHERE bt.bookmark.id = :bookmarkId) ORDER BY t.tag.tag ASC"
		)
})
public class Tag {
	private long id;
	private String tag;
	private Set<Bookmark> bookmarks;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="TAG", length=255, unique=true)
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
   		name="BOOKMARK_TAGS",
    	joinColumns = { @JoinColumn(name="TAG_ID") },
    	inverseJoinColumns = { @JoinColumn(name="BOOKMARK_ID") })	
	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}
	
	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}
}
