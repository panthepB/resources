package distar.project.service;

public class IndexService {
	private int i;

	public IndexService() {
		i = 0;
	}

	public int getI() {
		i++;
		return this.i;
	}

	public int getIndex() {
		return this.i;
	}

	// for duang's report
	public int getNo() {
		return getIndex();
	}
}
