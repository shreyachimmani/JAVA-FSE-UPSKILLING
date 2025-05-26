--1. User Upcoming Events:
SELECT u.user_id, u.full_name, e.title AS event_title, e.city, e.start_date
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
JOIN Events e ON r.event_id = e.event_id
WHERE e.status = 'upcoming' AND u.city = e.city
ORDER BY e.start_date;


--2. Top Rated Events:
SELECT e.event_id, e.title, AVG(f.rating) AS avg_rating, COUNT(f.feedback_id) AS feedback_count
FROM Events e
JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(f.feedback_id) >= 10
ORDER BY avg_rating DESC;


--3. Inactive Users (No Registrations in Last 90 Days):
SELECT u.user_id, u.full_name
FROM Users u
LEFT JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.user_id, u.full_name
HAVING MAX(r.registration_date) IS NULL 
    OR MAX(r.registration_date) < CURRENT_DATE - INTERVAL 90 DAY;


--4. Peak Session Hours (10 AM to 12 PM):
SELECT e.event_id, e.title, COUNT(s.session_id) AS session_count
FROM Events e
JOIN Sessions s ON e.event_id = s.event_id
WHERE TIME(s.start_time) BETWEEN '10:00:00' AND '11:59:59'
GROUP BY e.event_id, e.title;


--5. Most Active Cities:
SELECT u.city, COUNT(DISTINCT r.user_id) AS total_users
FROM Users u
JOIN Registrations r ON u.user_id = r.user_id
GROUP BY u.city
ORDER BY total_users DESC
LIMIT 5;


--6. Event Resource Summary:
SELECT e.event_id, e.title,
       SUM(CASE WHEN r.resource_type = 'pdf' THEN 1 ELSE 0 END) AS pdf_count,
       SUM(CASE WHEN r.resource_type = 'image' THEN 1 ELSE 0 END) AS image_count,
       SUM(CASE WHEN r.resource_type = 'link' THEN 1 ELSE 0 END) AS link_count
FROM Events e
LEFT JOIN Resources r ON e.event_id = r.event_id
GROUP BY e.event_id, e.title;


--7. Low Feedback Alerts (Rating < 3):
SELECT u.full_name, e.title AS event_title, f.rating, f.comments
FROM Feedback f
JOIN Users u ON f.user_id = u.user_id
JOIN Events e ON f.event_id = e.event_id
WHERE f.rating < 3;


--8. Sessions per Upcoming Event:
SELECT e.event_id, e.title, COUNT(s.session_id) AS session_count
FROM Events e
LEFT JOIN Sessions s ON e.event_id = s.event_id
WHERE e.status = 'upcoming'
GROUP BY e.event_id, e.title;


--9. Organizer Event Summary:
SELECT u.user_id AS organizer_id, u.full_name,
       COUNT(e.event_id) AS total_events,
       SUM(CASE WHEN e.status = 'upcoming' THEN 1 ELSE 0 END) AS upcoming_events,
       SUM(CASE WHEN e.status = 'completed' THEN 1 ELSE 0 END) AS completed_events,
       SUM(CASE WHEN e.status = 'cancelled' THEN 1 ELSE 0 END) AS cancelled_events
FROM Users u
JOIN Events e ON u.user_id = e.organizer_id
GROUP BY u.user_id, u.full_name;


--10. Feedback Gap (Events with Registrations but No Feedback):
SELECT e.event_id, e.title
FROM Events e
JOIN Registrations r ON e.event_id = r.event_id
LEFT JOIN Feedback f ON e.event_id = f.event_id
GROUP BY e.event_id, e.title
HAVING COUNT(DISTINCT f.feedback_id) = 0;

--11. Daily New User Count:
SELECT registration_date, COUNT(*) AS new_users
FROM Users
WHERE registration_date >= CURDATE() - INTERVAL 7 DAY
GROUP BY registration_date;

--12. Event with Maximum Sessions:
SELECT event_id, COUNT(*) AS session_count
FROM Sessions
GROUP BY event_id
ORDER BY session_count DESC
LIMIT 1;

--13. Average Rating per City:
SELECT E.city, AVG(F.rating) AS avg_rating
FROM Feedback F
JOIN Events E ON F.event_id = E.event_id
GROUP BY E.city;

--14. Most Registered Events:
SELECT event_id, COUNT(*) AS registrations
FROM Registrations
GROUP BY event_id
ORDER BY registrations DESC
LIMIT 3;

--15. Event Session Time Conflict:
SELECT A.event_id, A.session_id AS session1, B.session_id AS session2
FROM Sessions A
JOIN Sessions B ON A.event_id = B.event_id AND A.session_id < B.session_id
WHERE A.start_time < B.end_time AND B.start_time < A.end_time;

--16. Unregistered Active Users:
SELECT *
FROM Users
WHERE registration_date >= CURDATE() - INTERVAL 30 DAY
AND user_id NOT IN (
    SELECT DISTINCT user_id FROM Registrations
);

--17. Multi-Session Speakers:
SELECT speaker_name, COUNT(*) AS session_count
FROM Sessions
GROUP BY speaker_name
HAVING COUNT(*) > 1;

--18. Resource Availability Check:
SELECT E.event_id, E.title
FROM Events E
LEFT JOIN Resources R ON E.event_id = R.event_id
WHERE R.resource_id IS NULL;

--19. Completed Events with Feedback Summary:
SELECT E.event_id, COUNT(DISTINCT R.user_id) AS total_registrations, AVG(F.rating) AS avg_rating
FROM Events E
LEFT JOIN Registrations R ON E.event_id = R.event_id
LEFT JOIN Feedback F ON E.event_id = F.event_id
WHERE E.status = 'completed'
GROUP BY E.event_id;

--20. User Engagement Index:
SELECT U.user_id, 
       COUNT(DISTINCT R.event_id) AS events_attended,
       COUNT(DISTINCT F.feedback_id) AS feedbacks_given
FROM Users U
LEFT JOIN Registrations R ON U.user_id = R.user_id
LEFT JOIN Feedback F ON U.user_id = F.user_id
GROUP BY U.user_id;

--21. Top Feedback Providers:
SELECT user_id, COUNT(*) AS feedback_count
FROM Feedback
GROUP BY user_id
ORDER BY feedback_count DESC
LIMIT 5;

--22. Duplicate Registrations Check:
SELECT user_id, event_id, COUNT(*) AS times_registered
FROM Registrations
GROUP BY user_id, event_id
HAVING COUNT(*) > 1;

--23. Registration Trends:
SELECT DATE_FORMAT(registration_date, '%Y-%m') AS month, COUNT(*) AS registration_count
FROM Users
GROUP BY month
ORDER BY month;

--24. Average Session Duration per Event:
SELECT event_id, 
       AVG(TIMESTAMPDIFF(MINUTE, start_time, end_time)) AS avg_duration_minutes
FROM Sessions
GROUP BY event_id;

--25. Events Without Sessions:
SELECT E.event_id, E.title
FROM Events E
LEFT JOIN Sessions S ON E.event_id = S.event_id
WHERE S.session_id IS NULL;
