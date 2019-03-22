package main.java.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import main.java.bean.WorkTask;

@SuppressWarnings("deprecation")
public class PaginationResult<E> {

	private int totalRecords;
	private int currentPage;
	private List<E> list;
	private int maxResult;
	private int totalPages;

	@SuppressWarnings({"unchecked" })
	public PaginationResult(Query<WorkTask> query, int page, int maxResult) {
		final int pageIndex = page - 1 < 0 ? 0 : page - 1;

		int fromRecordIndex = pageIndex * maxResult;
		int maxRecordIndex = fromRecordIndex + maxResult;

		ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

		List<E> results = new ArrayList<E>();

		boolean hasResult = resultScroll.first();

		if (hasResult) {

			// Scroll to position:
			hasResult = resultScroll.scroll(fromRecordIndex);

			if (hasResult) {
				do {
					E record = (E) resultScroll.get(0);
					results.add(record);
				} while (resultScroll.next()//
						&& resultScroll.getRowNumber() >= fromRecordIndex
						&& resultScroll.getRowNumber() < maxRecordIndex);

			}

			// Go to Last record.
			resultScroll.last();
		}

		// Total Records
		this.totalRecords = resultScroll.getRowNumber() + 1;
		this.currentPage = pageIndex + 1;
		this.list = results;
		this.maxResult = maxResult;

		if (this.totalRecords % this.maxResult == 0) {
			this.totalPages = this.totalRecords / this.maxResult;
		} else {
			this.totalPages = (this.totalRecords / this.maxResult) + 1;
		}

		resultScroll.close();

	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<E> getList() {
		return list;
	}

	public int getMaxResult() {
		return maxResult;
	}


}