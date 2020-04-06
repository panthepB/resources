package distar.project.service;

public class PagedNavHolder {
	public static final String NAME = "pageNav";
	private int page;
	private int pageSize;
	private long listCount;
	private int linkCount = 10;
	private int offset = 5;

	public int getLinkCount() {
		return linkCount;
	}

	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getListCount() {
		return listCount;
	}

	public void setListCount(long listCount) {
		this.listCount = listCount;
	}

	public int getPageCount() {
		return (int) Math.ceil((double) listCount / (double) pageSize);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page < 0)
			this.page = 0;
		// else if (page > getPageCount() -1)
		// this.page = getPageCount() -1;

		else
			this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isFirstPage() {
		return (this.page == 0);
	}

	public boolean isLastPage() {
		return (this.page == (getPageCount() - 1));
	}

	public int getFirstLinkedPage() {

		// determine the standard window
		int start = page - linkCount / 2;
		int end = start + linkCount - 1;
		// shift to right if start underflows 0
		if (start < 0) {
			end -= start; // end - -start = end + start = shift right
			start = 0;
		}

		int lastIndex = getPageCount() - 1;
		if (end > lastIndex) {
			start -= (end - lastIndex);
			end = lastIndex;
		}

		if (start < 0)
			start = 0;

		return start;

	}

	public int getLastLinkedPage() {
		// determine the standard window
		int start = page - linkCount / 2;
		int end = start + linkCount - 1;
		// shift to right if start underflows 0
		if (start < 0) {
			end -= start; // end - -start = end + start = shift right
			start = 0;
		}

		int lastIndex = getPageCount() - 1;
		if (end > lastIndex) {
			start -= (end - lastIndex);
			end = lastIndex;
		}

		if (start < 0)
			start = 0;

		return end;

	}

	public static int convParameter(String sp) {
		int p = 0;
		if (sp != null) {
			p = Integer.parseInt(sp);
		}
		return p;
	}

	public PagedNavHolder createHandler() {
		PagedNavHolder ph = new PagedNavHolder();
		ph.setPageSize(pageSize);
		ph.setLinkCount(linkCount);
		ph.setOffset(offset);
		return ph;
	}

	public int getStartIndex() {
		return page * pageSize;
	}
}
