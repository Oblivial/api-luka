package de.luka.api.search;


public class Search<T> {
	private Iterable<?> matches;
	private Class<T> matchObjectType;
	private String matchReason;
	
	public Search(Class<T> matchObjectType, String matchReason) {
		this.matchReason = matchReason;
		this.matchObjectType = matchObjectType;
	}
	
	public Iterable<?> getMatches() {
		return matches;
	}
	public void setMatches(Iterable<?> matches) {
		this.matches = matches;
	}
	public String getmatchObjectType() {
		return matchObjectType.getSimpleName();
	}
	public String getMatchReason() {
		return matchReason;
	}
	public void setMatchReason(String matchReason) {
		this.matchReason = matchReason;
	}
	
	
	
}
