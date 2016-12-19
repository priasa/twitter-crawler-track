package id.smarta.krakatau.streamer.twitter;

public enum TwitterUserID {
	mtf(786333896l),
	adira(2578289821l),
	fif(384193674l),
	bfi(159016742l);
	
	private Long userId;

	private TwitterUserID(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}
}
