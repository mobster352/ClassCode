#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <omp.h>

#define G                   6.67300e-11
#define BASE_MASS           5.9742e24
#define BASE_DIAMETER       12756000.32
#define TIMESTEP            1.0

#define MIN_DIST            0.75 * BASE_DIAMETER
#define MAX_MASS            2.00 * BASE_MASS
#define MASS_DIST_SQ        ((MIN_DIST*MIN_DIST) / MAX_MASS)

#define NUMBODIES           2000
#define NUMSTEPS            200


/*
 * body data structure 
 */
struct body_s {
  double mass;
  double x, y, z;             // position
  double vx, vy, vz;          // velocity
  double fx, fy, fz;          // force
  double xnew, ynew, znew;    // pos. in next timestep
  double vxnew, vynew, vznew; // vel. in next timestep
};
typedef struct body_s body_t;

/* global array (shared) of bodies */
body_t bodies[NUMBODIES];


/* function prototypes */
double   get_wctime(void);
double   drandom(double lo, double hi);
int      irandom(int lo, int hi);
double   distance(body_t* b1, body_t* b2);
double   unitvec(body_t* b1, body_t *b2, double *ux, double *uy, double *uz);



/* returns a timestamp from the wall-clock time */
double get_wctime(void) {
  struct timeval tv;
  gettimeofday(&tv, NULL);
  return (tv.tv_sec + 1E-6 * tv.tv_usec);
}



/*  returns a random double within the range lo-hi */
double   drandom(double lo, double hi) {
  double r = (double)rand();
  return lo + r * (hi-lo) / (double)RAND_MAX;
}



/*  returns a random integer within the range lo-hi */
int      irandom(int lo, int hi) {
  return (int)drandom((double)lo, (double)hi + .9999f);
}


/* returns the square of the distance between b1 and b2 */
double   distance(body_t *b1, body_t *b2) {
  double dx = b1->x - b2->x;
  double dy = b1->y - b2->y;
  double dz = b1->z - b2->z;
  return dx*dx + dy*dy + dz*dz;
}


/* returns the unit vector between two bodies */
double   unitvec(body_t *b1, body_t *b2, double *ux, double *uy, double *uz) {
  double dx = b1->x - b2->x;
  double dy = b1->y - b2->y;
  double dz = b1->z - b2->z;

  double d = sqrt(dx*dx + dy*dy + dz*dz);
  
  if (d > 0.0) {
    dx /= d;
    dy /= d;
    dz /= d;
  } 

  // copy out return values
  *ux = dx;
  *uy = dy;
  *uz = dz;
}


int main(int argc, char **argv) {
  int i,j, t;
  int numthreads = 1;
  
  // seed random number generator
  srand((unsigned int)get_wctime());

  if (argc == 2) {
    numthreads = atoi(argv[1]);
  } else {
    fprintf(stderr, "usage: %s <ncpus>\n", argv[0]);
    exit(-1);
  }

#ifdef _OPENMP
  printf("using %d processors\n", numthreads);
  omp_set_num_threads(numthreads);
#else
  printf("using 1 processor (no OpenMP directives used)\n");
#endif

  double start = get_wctime(); // timestamp beginning of run
  /* initialize body parameters */
  for (i=0; i<NUMBODIES; i++) {
    bodies[i].mass = BASE_MASS * drandom(.5,10.0);
    bodies[i].x    = BASE_DIAMETER * drandom(-100.0, 100.0);
    bodies[i].y    = BASE_DIAMETER * drandom(-100.0, 100.0);
    bodies[i].z    = BASE_DIAMETER * drandom(-100.0, 100.0);
    bodies[i].vx   = drandom(-100.0,100.0);
    bodies[i].vy   = drandom(-100.0,100.0);
    bodies[i].vz   = drandom(-100.0,100.0);
  }


  /* main simulation loop */
  for (t=0; t<NUMSTEPS; t++) {
    for (i=0; i<NUMBODIES; i++) {
      double fx = 0.0, fy = 0.0, fz = 0.0;
      body_t *b1 = &bodies[i]; // compute all the interaction forces for this body
      
      for (j=0; j<NUMBODIES; j++) {
	if (i!=j) {
	  
	  body_t *b2 = &bodies[j];
	  double rsq = distance(b1,b2);
	  double mass_rsq = rsq / b2->mass;

	  if (mass_rsq < MASS_DIST_SQ) {
	    double f = (G * b1->mass / mass_rsq);
	    double ux,uy,uz;
	    unitvec(b1, b2, &ux, &uy, &uz);
	    fx += f * ux;
	    fy += f * uy;
	    fz += f * uz;
	  }
	}
      }

      double ax = fx / bodies[i].mass;
      double ay = fy / bodies[i].mass;
      double az = fz / bodies[i].mass;
      
      bodies[i].xnew = bodies[i].x + bodies[i].vx*TIMESTEP + 0.5*ax*TIMESTEP*TIMESTEP;
      bodies[i].ynew = bodies[i].y + bodies[i].vy*TIMESTEP + 0.5*ay*TIMESTEP*TIMESTEP;
      bodies[i].znew = bodies[i].z + bodies[i].vz*TIMESTEP + 0.5*az*TIMESTEP*TIMESTEP;
      
      bodies[i].vxnew = bodies[i].vx + ax*TIMESTEP;
      bodies[i].vynew = bodies[i].vy + ay*TIMESTEP;
      bodies[i].vznew = bodies[i].vz + az*TIMESTEP;
    }
    
    // update all bodies for the next timestep
    for (i=0;i<NUMBODIES;i++) {
      bodies[i].x = bodies[i].xnew;
      bodies[i].y = bodies[i].ynew;
      bodies[i].z = bodies[i].znew;
      bodies[i].vx = bodies[i].vxnew;
      bodies[i].vy = bodies[i].vynew;
      bodies[i].vz = bodies[i].vznew;
    }
  }
  printf("n-body simulation took %7.3f secs\n", (get_wctime() - start));

  return 0;
}
