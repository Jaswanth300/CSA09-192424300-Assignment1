import matplotlib.pyplot as plt
import matplotlib.patches as patches
from matplotlib.patches import FancyBboxPatch, Polygon
import os

os.makedirs("D:/Data/Development/College/Java/Assessment-1/diagrams", exist_ok=True)

def draw_uml_box(ax, x, y, w, h, title, stereotype="", attrs=None, methods=None, header_bg="#1A365D", body_bg="#F8FAFC"):
    # Header height
    h_header = 0.38 if stereotype else 0.28
    
    # Outer box (body)
    body = FancyBboxPatch((x, y), w, h, boxstyle="round,pad=0.02,rounding_size=0.04",
                          linewidth=1.2, edgecolor="#2D3748", facecolor=body_bg, zorder=2)
    ax.add_patch(body)
    
    # Header clip
    header = FancyBboxPatch((x, y + h - h_header), w, h_header, boxstyle="round,pad=0.02,rounding_size=0.04",
                            linewidth=1.2, edgecolor="#2D3748", facecolor=header_bg, zorder=3)
    ax.add_patch(header)
    
    # Title & Stereotype text
    if stereotype:
        ax.text(x + w/2, y + h - 0.12, f"<<{stereotype}>>", color="#E2E8F0", fontsize=8,
                ha="center", va="center", style="italic", zorder=4, weight="normal")
        ax.text(x + w/2, y + h - 0.26, title, color="#FFFFFF", fontsize=9.5,
                ha="center", va="center", weight="bold", zorder=4)
    else:
        ax.text(x + w/2, y + h - h_header/2, title, color="#FFFFFF", fontsize=9.5,
                ha="center", va="center", weight="bold", zorder=4)
        
    curr_y = y + h - h_header - 0.08
    
    # Attributes
    if attrs:
        for attr in attrs:
            ax.text(x + 0.08, curr_y, attr, color="#1A202C", fontsize=7.5,
                    ha="left", va="center", zorder=4, family="monospace")
            curr_y -= 0.14
        # Divider line between attrs and methods
        curr_y -= 0.04
        ax.plot([x, x + w], [curr_y, curr_y], color="#CBD5E0", lw=0.8, zorder=3)
        curr_y -= 0.08
    
    # Methods
    if methods:
        for method in methods:
            is_abstract = method.endswith("*") or "*" in method
            clean_m = method.replace("*", "")
            ax.text(x + 0.08, curr_y, clean_m, color="#1A202C", fontsize=7.5,
                    ha="left", va="center", zorder=4, family="monospace",
                    style="italic" if is_abstract else "normal")
            curr_y -= 0.14

def draw_inheritance_arrow(ax, x1, y1, x2, y2, dashed=False):
    # Line from (x1, y1) to (x2, y2 - 0.15)
    lw = 1.0
    ls = "--" if dashed else "-"
    ax.plot([x1, x2], [y1, y2 - 0.16], color="#2D3748", lw=lw, linestyle=ls, zorder=1)
    
    # Closed triangle pointing up at (x2, y2)
    tri = Polygon([[x2, y2], [x2 - 0.08, y2 - 0.16], [x2 + 0.08, y2 - 0.16]],
                  closed=True, facecolor="#FFFFFF", edgecolor="#2D3748", lw=1.0, zorder=5)
    ax.add_patch(tri)

def draw_aggregation_arrow(ax, x1, y1, x2, y2, composition=False, label=""):
    lw = 1.0
    ax.plot([x1 + 0.14, x2], [y1, y2], color="#2D3748", lw=lw, zorder=1)
    
    # Diamond at (x1, y1)
    d_color = "#1A202C" if composition else "#FFFFFF"
    dia = Polygon([[x1, y1], [x1 + 0.07, y1 + 0.06], [x1 + 0.14, y1], [x1 + 0.07, y1 - 0.06]],
                  closed=True, facecolor=d_color, edgecolor="#2D3748", lw=1.0, zorder=5)
    ax.add_patch(dia)
    if label:
        ax.text((x1 + x2)/2, (y1 + y2)/2 + 0.05, label, fontsize=7.5, color="#4A5568", ha="center")

# ==============================================================================
# Diagram 1: Task 6 - Inheritance Hierarchy
# ==============================================================================
fig, ax = plt.subplots(figsize=(8.5, 5.0), dpi=300)
ax.set_xlim(0, 9)
ax.set_ylim(0, 5)
ax.axis("off")

draw_uml_box(ax, 3.1, 3.2, 2.8, 1.45, "Vehicle", stereotype="abstract",
             attrs=["- regNo: String"],
             methods=["+ Vehicle(regNo: String)", "+ getRegNo(): String", "+ ratePerHour()*: double", "+ toString(): String"])

draw_uml_box(ax, 0.8, 1.4, 2.6, 1.1, "TwoWheeler",
             attrs=[],
             methods=["+ TwoWheeler(regNo: String)", "+ ratePerHour(): double"])

draw_uml_box(ax, 5.6, 1.4, 2.6, 1.1, "Car",
             attrs=[],
             methods=["+ Car(regNo: String)", "+ ratePerHour(): double"])

draw_uml_box(ax, 5.4, 0.05, 3.0, 1.05, "ElectricCar",
             attrs=["- batteryCapacity: int"],
             methods=["+ ElectricCar(regNo: String, batteryCapacity: int)"])

# Connections
draw_inheritance_arrow(ax, 2.1, 2.5, 3.8, 3.2) # TwoWheeler -> Vehicle
draw_inheritance_arrow(ax, 6.9, 2.5, 5.2, 3.2) # Car -> Vehicle
draw_inheritance_arrow(ax, 6.9, 1.1, 6.9, 1.4) # ElectricCar -> Car

ax.text(4.5, 4.85, "UML Class Diagram: Vehicle Inheritance Hierarchy (Task 6)",
        fontsize=11, weight="bold", color="#1A365D", ha="center")

plt.tight_layout()
fig.savefig("D:/Data/Development/College/Java/Assessment-1/diagrams/task06_inheritance.png", bbox_inches="tight")
plt.close(fig)

# ==============================================================================
# Diagram 2: Task 10 - Package Modular Architecture
# ==============================================================================
fig, ax = plt.subplots(figsize=(9.0, 4.8), dpi=300)
ax.set_xlim(0, 9.5)
ax.set_ylim(0, 5)
ax.axis("off")

# Package parking.model
pkg_model = FancyBboxPatch((0.4, 0.3), 3.8, 4.1, boxstyle="round,pad=0.03",
                           fc="#EDF2F7", ec="#4A5568", lw=1.2, ls="--")
ax.add_patch(pkg_model)
ax.text(0.6, 4.15, "package parking.model", weight="bold", fontsize=9, color="#2D3748")

draw_uml_box(ax, 0.6, 2.4, 3.4, 1.5, "Vehicle", stereotype="abstract",
             attrs=["- regNo: String"], methods=["+ getRegNo(): String", "+ ratePerHour()*: double", "+ calculateFee(min: int): double"])
draw_uml_box(ax, 0.6, 0.6, 1.6, 1.4, "ParkingSpot",
             attrs=["- floor: int", "- slot: int"], methods=["+ park(v): bool", "+ vacate(): Vehicle"])
draw_uml_box(ax, 2.4, 0.6, 1.6, 1.4, "Car / TwoWheeler",
             attrs=[], methods=["+ ratePerHour(): double"])

# Package parking.service
pkg_service = FancyBboxPatch((4.6, 1.8), 4.5, 2.6, boxstyle="round,pad=0.03",
                             fc="#EBF8FF", ec="#3182CE", lw=1.2, ls="--")
ax.add_patch(pkg_service)
ax.text(4.8, 4.15, "package parking.service", weight="bold", fontsize=9, color="#2B6CB0")

draw_uml_box(ax, 4.8, 2.1, 4.1, 1.6, "ParkingService",
             attrs=["- facility: ParkingSpot[][]"],
             methods=["+ allocateSpot(v: Vehicle): ParkingSpot",
                      "+ calculateFee(v: Vehicle, min: int): double"])

# Package parking.app
pkg_app = FancyBboxPatch((4.6, 0.3), 4.5, 1.3, boxstyle="round,pad=0.03",
                         fc="#F0FFF4", ec="#38A169", lw=1.2, ls="--")
ax.add_patch(pkg_app)
ax.text(4.8, 1.35, "package parking.app", weight="bold", fontsize=9, color="#276749")

draw_uml_box(ax, 4.8, 0.45, 4.1, 0.8, "MainApp",
             attrs=[], methods=["+ main(args: String[]): void"])

# Inter-package dependency arrows
ax.annotate("", xy=(4.2, 3.0), xytext=(4.8, 3.0),
            arrowprops=dict(arrowstyle="->", lw=1.2, color="#2B6CB0", ls="--"))
ax.text(4.5, 3.15, "uses", fontsize=7.5, color="#2B6CB0", ha="center")

ax.annotate("", xy=(6.85, 1.8), xytext=(6.85, 1.25),
            arrowprops=dict(arrowstyle="->", lw=1.2, color="#276749", ls="--"))
ax.text(7.2, 1.5, "invokes", fontsize=7.5, color="#276749", ha="center")

ax.text(4.75, 4.75, "Package Modular Architecture & Access Encapsulation (Task 10)",
        fontsize=11, weight="bold", color="#1A365D", ha="center")

plt.tight_layout()
fig.savefig("D:/Data/Development/College/Java/Assessment-1/diagrams/task10_packages.png", bbox_inches="tight")
plt.close(fig)

# ==============================================================================
# Diagram 3: Task 11 - Core Integrated System Class Diagram
# ==============================================================================
fig, ax = plt.subplots(figsize=(10.5, 6.8), dpi=300)
ax.set_xlim(0, 11)
ax.set_ylim(0, 7.5)
ax.axis("off")

# Chargeable Interface
draw_uml_box(ax, 0.5, 5.4, 2.5, 0.95, "Chargeable", stereotype="interface",
             attrs=[], methods=["+ chargingFee(units: double): double"], header_bg="#2B6CB0")

# Abstract Vehicle
draw_uml_box(ax, 3.8, 4.6, 3.4, 2.3, "Vehicle", stereotype="abstract",
             attrs=["~ regNo: String", "~ monthlyPass: boolean"],
             methods=["+ Vehicle(regNo, monthlyPass)",
                      "+ ratePerHour()*: double", "+ parkingFee(min: int): double",
                      "+ equals(obj: Object): boolean", "+ hashCode(): int"])

# Subclasses
draw_uml_box(ax, 0.5, 3.2, 2.5, 1.1, "TwoWheeler",
             attrs=[], methods=["+ TwoWheeler(regNo, pass)", "+ ratePerHour(): double"])

draw_uml_box(ax, 3.8, 3.2, 2.5, 1.1, "Car",
             attrs=[], methods=["+ Car(regNo, pass)", "+ ratePerHour(): double"])

draw_uml_box(ax, 0.5, 1.6, 2.5, 1.1, "ElectricCar",
             attrs=[], methods=["+ ElectricCar(regNo, pass)", "+ chargingFee(units: double): double"])

# ParkingTicket
draw_uml_box(ax, 7.8, 4.2, 2.7, 2.2, "ParkingTicket",
             attrs=["~ vehicle: Vehicle", "~ start: int", "~ floor: int", "~ slot: int"],
             methods=["+ ParkingTicket(v, start, f, s)"])

# ParkingFacility
draw_uml_box(ax, 4.0, 0.4, 6.5, 1.8, "ParkingFacility",
             attrs=["~ occupied: boolean[][]", "~ tickets: ArrayList<ParkingTicket>", "~ revenue: double"],
             methods=["+ checkIn(v: Vehicle, time: int): ParkingTicket",
                      "+ checkOut(t: ParkingTicket, time: int, units: double): double"])

# Arrows
draw_inheritance_arrow(ax, 1.75, 4.3, 4.5, 4.6) # TwoWheeler -> Vehicle
draw_inheritance_arrow(ax, 5.05, 4.3, 5.05, 4.6) # Car -> Vehicle
draw_inheritance_arrow(ax, 1.75, 2.7, 4.5, 3.2) # ElectricCar -> Car
draw_inheritance_arrow(ax, 1.75, 2.7, 1.75, 5.4, dashed=True) # ElectricCar ..> Chargeable

draw_aggregation_arrow(ax, 7.8, 5.2, 7.2, 5.2, composition=False, label="references 1") # Ticket -> Vehicle
draw_aggregation_arrow(ax, 8.5, 2.2, 8.5, 4.2, composition=False, label="tickets: List") # Facility -> Ticket

ax.text(5.5, 7.2, "Complete System Object-Oriented Architecture (Task 11)",
        fontsize=12, weight="bold", color="#1A365D", ha="center")

plt.tight_layout()
fig.savefig("D:/Data/Development/College/Java/Assessment-1/diagrams/task11_core_system.png", bbox_inches="tight")
plt.close(fig)

# ==============================================================================
# Diagram 4: Task 12 - Day-4 Adapted Architecture (Strategy Pattern)
# ==============================================================================
fig, ax = plt.subplots(figsize=(10.5, 6.8), dpi=300)
ax.set_xlim(0, 11)
ax.set_ylim(0, 7.5)
ax.axis("off")

# Pass Interface & Impl
draw_uml_box(ax, 0.4, 5.5, 2.5, 1.1, "Pass", stereotype="interface",
             attrs=[], methods=["+ isValid(hours: int): boolean"], header_bg="#2B6CB0")
draw_uml_box(ax, 0.4, 3.8, 2.5, 1.25, "OneDayPass",
             attrs=["~ price: double = 100"],
             methods=["+ isValid(hours: int): boolean"])
draw_inheritance_arrow(ax, 1.65, 5.05, 1.65, 5.5, dashed=True)

# PricingStrategy Interface & Impls
draw_uml_box(ax, 7.5, 5.5, 3.1, 1.1, "PricingStrategy", stereotype="interface",
             attrs=[], methods=["+ calculateFee(hours: int): double"], header_bg="#2B6CB0")

draw_uml_box(ax, 7.5, 3.9, 3.1, 1.15, "HourlyPricing",
             attrs=["~ rate: double"], methods=["+ calculateFee(hours: int): double"])
draw_inheritance_arrow(ax, 9.05, 5.05, 9.05, 5.5, dashed=True)

draw_uml_box(ax, 7.5, 2.4, 3.1, 1.15, "FlatPricing",
             attrs=["~ fee: double"], methods=["+ calculateFee(hours: int): double"])
draw_inheritance_arrow(ax, 9.05, 3.55, 9.05, 3.9, dashed=True)

draw_uml_box(ax, 7.5, 0.5, 3.1, 1.55, "DeliveryPricing",
             attrs=[],
             methods=["+ calculateFee(hours: int): double", "+ offPeakFee(hours: int): double"])
draw_inheritance_arrow(ax, 9.05, 2.05, 9.05, 2.4, dashed=True)

# ParkingVehicle using Strategy
draw_uml_box(ax, 3.6, 4.2, 3.3, 1.8, "ParkingVehicle", stereotype="",
             attrs=["~ regNo: String", "~ pricing: PricingStrategy"],
             methods=["+ ParkingVehicle(regNo, pricing)", "+ fee(hours: int): double"])

# Strategy Association
draw_aggregation_arrow(ax, 6.9, 5.2, 7.5, 5.8, composition=False, label="pricing")

# Adapted Parking Facility
draw_uml_box(ax, 0.4, 0.5, 6.5, 1.8, "AdaptedParkingFacility",
             attrs=["~ floors: String[][] [5 floors]"],
             methods=["+ park(regNo: String): boolean", "+ remove(regNo: String): boolean",
                      "+ totalSlots(): int"])

ax.text(5.5, 7.2, "Adapted Architecture: Strategy Pattern & Extensible Design (Task 12)",
        fontsize=12, weight="bold", color="#1A365D", ha="center")

plt.tight_layout()
fig.savefig("D:/Data/Development/College/Java/Assessment-1/diagrams/task12_adapted_system.png", bbox_inches="tight")
plt.close(fig)

# ==============================================================================
# Diagram 5: Sequence Diagram - Vehicle Check-In & Slot Allocation
# ==============================================================================
fig, ax = plt.subplots(figsize=(9.5, 5.2), dpi=300)
ax.set_xlim(0, 10)
ax.set_ylim(0, 6)
ax.axis("off")

# Lifelines
lifelines = [
    ("Driver", 1.0),
    ("ParkingGate", 3.0),
    ("ParkingFacility", 5.0),
    ("ParkingSpot", 7.0),
    ("ParkingTicket", 9.0)
]

for name, x in lifelines:
    ax.add_patch(FancyBboxPatch((x - 0.7, 5.1), 1.4, 0.5, boxstyle="round,pad=0.02",
                                facecolor="#2B6CB0", edgecolor="#1A365D", lw=1.2))
    ax.text(x, 5.35, name, color="white", weight="bold", fontsize=8.5, ha="center", va="center")
    ax.plot([x, x], [0.6, 5.1], color="#A0AEC0", linestyle="--", lw=1.0, zorder=1)

def draw_seq_msg(ax, x1, x2, y, text, is_reply=False, num=1):
    ls = "--" if is_reply else "-"
    color = "#4A5568" if is_reply else "#1A202C"
    ax.annotate("", xy=(x2, y), xytext=(x1, y),
                arrowprops=dict(arrowstyle="->", lw=1.1, color=color, ls=ls))
    ax.text((x1 + x2)/2, y + 0.1, f"{num}. {text}" if num else text,
            fontsize=7.5, color=color, weight="bold" if not is_reply else "normal",
            ha="center", backgroundcolor="white")

draw_seq_msg(ax, 1.0, 3.0, 4.5, "presentVehicle(regNo, type)", num=1)
draw_seq_msg(ax, 3.0, 5.0, 4.0, "checkIn(vehicle)", num=2)
draw_seq_msg(ax, 5.0, 7.0, 3.4, "isAvailable()", num=3)
draw_seq_msg(ax, 7.0, 5.0, 2.9, "true (Floor 0, Slot 0)", is_reply=True, num=4)
draw_seq_msg(ax, 5.0, 7.0, 2.4, "park(vehicle)", num=5)
draw_seq_msg(ax, 5.0, 9.0, 1.9, "new ParkingTicket(v, 0, 0)", num=6)
draw_seq_msg(ax, 9.0, 5.0, 1.4, "return ticket (TKT-0001)", is_reply=True, num=7)
draw_seq_msg(ax, 5.0, 3.0, 1.0, "return ticket", is_reply=True, num=8)
draw_seq_msg(ax, 3.0, 1.0, 0.6, "issueTicket & openBarrier", num=9)

ax.text(5.0, 5.8, "UML Sequence Diagram: Vehicle Check-In & Slot Allocation",
        fontsize=11, weight="bold", color="#1A365D", ha="center")

plt.tight_layout()
fig.savefig("D:/Data/Development/College/Java/Assessment-1/diagrams/sequence_checkin.png", bbox_inches="tight")
plt.close(fig)

print("All 5 diagrams successfully generated.")
