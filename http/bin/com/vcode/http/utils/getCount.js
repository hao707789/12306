function L(l, m) {
	rt = "";
	seat_1 = -1;
	seat_2 = -1;
	i = 0;
	while (i < l.length) {
		s = l.substr(i, 10);
		c_seat = s.substr(0, 1);
		if (c_seat == m) {
			count = s.substr(6, 4);
			while (count.length > 1 && count.substr(0, 1) == "0") {
				count = count.substr(1, count.length)
			}
			count = parseInt(count);
			if (count < 3000) {
				seat_1 = count
			} else {
				seat_2 = (count - 3000)
			}
		}
		i = i + 10
	}
	if (seat_1 > -1) {
		rt += seat_1
	}
	if (seat_2 > -1) {
		rt += "," + seat_2
	}
	return rt
}