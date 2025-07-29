In this project I tried to re-create the basic principles behind the Raycasting approach used to render 3D object on a 2D screen. This was the approach used in some 90s games like Wolfenstein 3D.<br>
In this approach, the distance between the player and the objects in front of him is calculated through a series of rays, each of which has a slightly different angle from the others.<br>
Each calculated distance is then used to draw a thin rectangle, that represent the portion of the object, seen by the character through a specific ray. The union of all the thin rectangles composes the rendering of the object.<br>
When the character moves closer to the object, the distance decreases, and the rectangle get taller, when the character moves further away from the object the distance increase and the rectangle gets smaller.<br>

<img width="628" height="497" alt="raycasting" src="https://github.com/user-attachments/assets/ba3facea-125c-4dfd-a948-d663ffb3f9b6" /><br>
To calculate the distance from the character (red dot) to an object (blue rectangle), through a ray (black line) at an angle (α) I used the schema above.<br><br>
I reduced the problem into 4 subproblem, considering different cases according to the angle of the ray. (In this project i consider positive angles going clockwise, so for example α1 is greater than 0 and α3 is lower than 0).<br>

In all of the cases above we have an intersection point with an x coordinate equal to x0 (the same logic with x1 can be applied for the right side of the object). The y coordinate of the intersection point is calculated as yp = Math.round(yc+((x0-xc)*Math.tan(α))).
The distance through the ray is calculated as Math.abs(((x0-xc))/Math.cos(α)).<br>

If yp is between y0 and y1 the calculated distance is valid, otherwise the program consider the max possible distance.<br>

A similar approach can be used to calculate the distance from the top and bottom side of the object, using Math.round(xc + ((y0 - yc) * Math.tan((Math.PI / 2) - angle))) for the intersection point (in this case we are calculating the x coordinate) and Math.abs(((y0 - yc)) / Math.cos((Math.PI / 2) - angle)) for the distance.<br>

After we calculate the distance for each ray, we use them to draw rectangles of different sizes, that represent the object that the character is seeing.<br>

I wrote this code knowing just some of the theory behind this kind of approach, so it is very likely that the math is different from what they actually used back in the days.<br>

The result is quite similar to what I had in mind before I started writing this code, but it's pretty obvious that the rendering as I did it suffers from a heavy fisheye effect, which is probably caused by how i calculate the heights of the rectangles when casting the rays, or maybe by the distribution of the rays themselves in the field of view of the character.<br>
<img width="1466" height="805" alt="Screenshot 2025-07-29 171535" src="https://github.com/user-attachments/assets/1fc3075a-d95d-4df8-8e48-8a8e96a33df5" />
