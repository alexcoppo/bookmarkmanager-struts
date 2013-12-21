package it.webalice.alexcoppo.bookmarksmanager_struts.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name="BOOKMARK_TAGS")
public class BookmarkTag implements Serializable {
	private static final long serialVersionUID = -3932168352298724398L;
	private Bookmark bookmark;
	private Tag tag;

	@Id
	@ManyToOne
	@JoinColumn(name="BOOKMARK_ID", nullable=false)
	public Bookmark getBookmark() {
		return bookmark;
	}
	
	public void setBookmark(Bookmark bookmark) {
		this.bookmark = bookmark;
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name="TAG_ID", nullable=false)
	public Tag getTag() {
		return tag;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().
			append(bookmark).
			append(tag).
			toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		   
		BookmarkTag rhs = (BookmarkTag)obj;
		
		return new EqualsBuilder().
			append(bookmark, rhs.bookmark).
			append(tag, rhs.tag).
			isEquals();
	}
}
