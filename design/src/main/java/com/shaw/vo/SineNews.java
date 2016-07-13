package com.shaw.vo;

import com.shaw.bo.News;

public class SineNews {
	String title;
	String pic;
	String intro;
	String link;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public News toNews() {
		News news = new News();
		news.setCreatetime(System.currentTimeMillis());
		news.setDetail(this.intro);
		news.setImg(this.pic);
		news.setTitle(this.title);
		news.setType(1);
		news.setUid(1);
		news.setUname("admin");
		news.setUrl(this.link);
		return news;
	}

	@Override
	public String toString() {
		return "SineNews [title=" + title + ", pic=" + pic + ", intro=" + intro + ", link=" + link + "]";
	}

}
